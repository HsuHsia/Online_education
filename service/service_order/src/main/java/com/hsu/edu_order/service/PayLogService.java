package com.hsu.edu_order.service;

import com.hsu.edu_order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-17
 */
public interface PayLogService extends IService<PayLog> {

    Map createPayQRCode(String orderId);

    void updateOrderStatus(Map<String, String> map);

    Map<String, String> queryPayStatus(String orderId);
}
