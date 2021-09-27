package com.hsu.edu_order.controller;


import com.hsu.commonutils.R;
import com.hsu.edu_order.service.PayLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-17
 */
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {
    @Resource
    private PayLogService payLogService;

    // 生成支付的二维码
    @GetMapping("/createPayQRCode/{orderId}")
    public R createPayQRCode(@PathVariable String orderId) {
        Map map = payLogService.createPayQRCode(orderId);
        return R.ok().data(map);
    }
}

