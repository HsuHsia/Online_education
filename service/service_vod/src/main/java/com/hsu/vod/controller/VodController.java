package com.hsu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.hsu.commonutils.R;
import com.hsu.servicebase.exceptionhandler.SummerException;
import com.hsu.vod.Utils.ConstantVodUtil;
import com.hsu.vod.Utils.InitVodClient;
import com.hsu.vod.service.VodService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("eduvod/video")
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

    @GetMapping("getPalyAuth/{videoSourceId}")
    public R getPalyAuth(@PathVariable String videoSourceId) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoSourceId);
            String auth = client.getAcsResponse(request).getPlayAuth();
            return R.ok().data("playAuth", auth);
        }catch (Exception e) {
            throw new SummerException(20001, "获取视频凭证失败");
        }
    }
}
