package com.hsu.edu_order.remoteClient;

import com.hsu.commonutils.orderVo.UcenterMemberOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-ucenter")
@Component
public interface UCenterClient {
    @PostMapping("/ucenter/member/getUserMemberInfoOrder/{userId}")
    UcenterMemberOrderVo getUserMemberInfoOrder(@PathVariable("userId") String userId);
}
