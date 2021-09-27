package com.hsu.edu_order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.hsu.edu_order.entity.Order;
import com.hsu.edu_order.entity.PayLog;
import com.hsu.edu_order.mapper.PayLogMapper;
import com.hsu.edu_order.service.OrderService;
import com.hsu.edu_order.service.PayLogService;
import com.hsu.edu_order.utils.HttpClient;
import com.hsu.servicebase.exceptionhandler.SummerException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-17
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Resource
    private OrderService orderService;

    @Override
    public Map createPayQRCode(String orderId) {
        try{
            // 根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderId);
            Order order = orderService.getOne(wrapper);

            // 使用map设置生成二维码需要参数
            Map m = new HashMap();
            m.put("appid", "wx74862e0dfcf69954");                           // 商家的微信ID
            m.put("mch_id", "1558950191");                                  // 商户号
            m.put("nonce_str", WXPayUtil.generateNonceStr());               // 随机字符串已生成不同的二维码
            m.put("body", order.getCourseTitle());                          // 生成二维码的名字
            m.put("out_trade_no", orderId);                                 // 生成二维码标识
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+" ");      // 生成二维码价格
            m.put("spbill_create_ip", "127.0.0.1");                         // 支付的IP地址
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\\n");    // 支付之后的回调地址
            m.put("trade_type", "NATIVE");                                  // 支付类型

            // 发送httpclient请求，传递xml格式的参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

            // 3、设置client参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            // 得到请求返回的结果
            String xml = client.getContent();
            Map<String, String> result = WXPayUtil.xmlToMap(xml);
            // 封装结果
            Map map = new HashMap<>();
            map.put("out_trade_no", orderId);                       // 生成二维码标识
            map.put("course_id", order.getCourseId());              // 课程ID
            map.put("total_fee", order.getTotalFee());              // 课程价格
            map.put("result_code", result.get("result_code"));      // 二维码操作状态码
            map.put("code_url", result.get("code_url"));            // 二维码地址

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
//            redisTemplate.opsForValue().set(orderId, map, 20, TimeUnit.MINUTES);
            return map;
        }catch (Exception e) {
            throw new SummerException(20001, "生成接口失败");
        }
    }


    // 查询支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderId) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderId);
            m.put("nonce_str", WXPayUtil.generateNonceStr());  // 随机字符串

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");

        //根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        if(order.getStatus().intValue() == 1) return;
        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志
        PayLog payLog=new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); // 流水号
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表

    }
}
