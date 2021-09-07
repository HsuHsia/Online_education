package com.hsu.vod.controller;

import com.hsu.commonutils.R;
import com.hsu.vod.service.VodService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("eduvod/video")
@CrossOrigin
public class VodController {
    @Resource
    private VodService vodService;

    @PostMapping("/uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        String videoId = vodService.uplaodAliyunVideo(file);
        return R.ok().data("videoId", videoId);
    }

    // 删除单个视频
    @DeleteMapping("/deleteAlyVideo/{videoId}")
    public R deleteAlyVideo(@PathVariable String videoId) {
        boolean flag = vodService.deleteAlyVideoById(videoId);
        return flag ? R.ok() : R.error();
    }

    // 删除多个视频
    @DeleteMapping("deleteMultiVideo")
    public R deleteMultiVideo(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.deleteMultiVideo(videoIdList);
        return R.ok();
    }
}
