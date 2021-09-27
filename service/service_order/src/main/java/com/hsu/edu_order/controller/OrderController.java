package com.hsu.edu_order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsu.commonutils.JwtUtils;
import com.hsu.commonutils.R;
import com.hsu.edu_order.entity.Order;
import com.hsu.edu_order.service.OrderService;
import com.hsu.edu_order.service.PayLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-17
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @Resource
    private PayLogService payLogService;

    // 生成订单
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        String orderId = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    // 查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("items", order);
    }

    // 根据订单号查询订单支付状态
    @GetMapping("queryPayStatus/{orderId}")
    public R queryPayStatus(@PathVariable String orderId) {
        Map<String, String> map = payLogService.queryPayStatus(orderId);
        if(map==null) {
            return R.error().message("支付出错了");
        }

        if(map.get("trade_state").equals("SUCCESS")){
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }

    // 根据课程id和用户id查询用户是否购买课程
    // 此方法在edu courseFrontController中被远程调用
    @GetMapping("isBuyCourse/{courseId}/{userId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String userId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", userId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        return count>0;
    }
}

