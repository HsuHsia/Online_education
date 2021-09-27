package com.hsu.staservice.remoteClient;

import com.hsu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UCenterClient {
    @PostMapping("/ucenter/member/getRegisterInfo/{date}")
    R getRegisterInfo(@PathVariable("date") String date);
}
