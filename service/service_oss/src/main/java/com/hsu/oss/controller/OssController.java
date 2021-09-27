package com.hsu.oss.controller;

import com.hsu.commonutils.R;
import com.hsu.oss.service.OssService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eduoss")
public class OssController {
    @Resource
    private OssService ossService;

    @PostMapping("/fileoss")
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }
}
