package com.hsu.edu_order.service.impl;

import com.hsu.commonutils.orderVo.CourseOrderVo;
import com.hsu.commonutils.orderVo.UcenterMemberOrderVo;
import com.hsu.edu_order.remoteClient.EduClient;
import com.hsu.edu_order.remoteClient.UCenterClient;
import com.hsu.edu_order.entity.Order;
import com.hsu.edu_order.mapper.OrderMapper;
import com.hsu.edu_order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsu.edu_order.utils.OrderNoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private EduClient eduClient;

    @Resource
    private UCenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String userId) {
        // 根据课程ID返回课程信息，远程调用edu模块中的方法
        CourseOrderVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        System.out.println(courseInfoOrder);

//         根据用户ID返回用户信息，远程调用ucenter模块中的方法
        UcenterMemberOrderVo userInfoOrder = ucenterClient.getUserMemberInfoOrder(userId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());                     // 订单号
        order.setCourseId(courseId);                                    // 课程id
        order.setCourseTitle(courseInfoOrder.getTitle());               // 课程名称
        order.setCourseCover(courseInfoOrder.getCover());               // 课程封面

        order.setTeacherName(courseInfoOrder.getTeacherName());         // 讲师姓名
        order.setTotalFee(courseInfoOrder.getPrice());                  // 课程价格

        order.setMemberId(userId);                                      // 用户ID
        order.setMobile(userInfoOrder.getMobile());                     // 手机号
        order.setNickname(userInfoOrder.getNickname());                 // 用户昵称
        order.setStatus(0);                                             // 订单状态 （0： 未支付     1：已支付）
        order.setPayType(1);                                            // 支付类型 （0： 支付宝     1：微信）
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
