package com.hsu.msmservice.controller;

import com.hsu.commonutils.R;
import com.hsu.msmservice.service.MsmService;
import com.hsu.msmservice.utils.RandomUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Resource
    private MsmService msmService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @GetMapping("/sendVerification/{phoneNumber}")
    public R sendVerification(@PathVariable String phoneNumber){
        // 从redis中获取验证码
        String verificationCode = redisTemplate.opsForValue().get(phoneNumber);
        if (!StringUtils.isEmpty(verificationCode)) {
            return R.ok();
        }
        // 生成随机验证码
        verificationCode = RandomUtil.getSixBitRandom();

        // 调用service中的方法付发送验证码
        boolean flag = msmService.sendVerification(verificationCode, phoneNumber);
        if (flag) {
            // 发送成功，将验证码存入redis并设置有效时间
            redisTemplate.opsForValue().set(phoneNumber, verificationCode, 30, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
