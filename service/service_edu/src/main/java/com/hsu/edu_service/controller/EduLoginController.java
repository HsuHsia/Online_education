package com.hsu.edu_service.controller;

import com.hsu.commonutils.R;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "name").data("avatar",
                "https://hbimg.huabanimg.com/27b3a10a7752a63f41e6dd499da75a5a5af58bc810ebb3-ZJTPHb_fw658/format/webp");

    }

}
