package com.hsu.edu_service.client;

import com.hsu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodClientImpl.class)
@Component
public interface VodClient {
    @DeleteMapping("eduvod/video/deleteAlyVideo/{videoId}")
    R deleteAlyVideo(@PathVariable("videoId") String videoId);

    // 删除多个视频
    @DeleteMapping("eduvod/video/deleteMultiVideo")
    R deleteMultiVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
