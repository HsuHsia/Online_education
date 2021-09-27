package com.hsu.edu_order.service;

import com.hsu.edu_order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-17
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String userId);

}
