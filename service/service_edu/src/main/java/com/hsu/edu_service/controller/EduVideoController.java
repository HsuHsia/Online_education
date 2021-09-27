package com.hsu.edu_service.controller;


import com.hsu.commonutils.R;
import com.hsu.edu_service.client.VodClient;
import com.hsu.edu_service.entity.EduVideo;
import com.hsu.edu_service.service.EduVideoService;
import com.hsu.servicebase.exceptionhandler.SummerException;
import io.swagger.annotations.Api;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
@Api(tags="视频管理")
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Resource
    private EduVideoService videoService;

    // 注入 vod_client
    @Resource
    private VodClient vodClient;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo video){
        videoService.save(video);
        return R.ok();
    }

    @GetMapping("/getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("video", eduVideo);
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return R.ok();
    }

    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        // 根据小节id获取视频id
        EduVideo video = videoService.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)) {
            // 删小节 同时删除视频
            R result = vodClient.deleteAlyVideo(videoSourceId);
            if (!result.getSuccess()) {
                throw new SummerException(20001,"删除视频失败,熔断器开启.....");
            }
        }

        boolean flag = videoService.removeById(videoId);
        return flag ? R.ok() : R.error();
    }
}

