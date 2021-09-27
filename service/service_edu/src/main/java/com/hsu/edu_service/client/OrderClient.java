package com.hsu.edu_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-order", fallback = OrderClientImpl.class)
public interface OrderClient {
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{userId}")
    boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("userId") String userId);
}
