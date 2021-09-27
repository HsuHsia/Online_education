package com.hsu.service_ucenter.controller;


import com.hsu.commonutils.JwtUtils;
import com.hsu.commonutils.R;
import com.hsu.commonutils.orderVo.CourseOrderVo;
import com.hsu.commonutils.orderVo.UcenterMemberOrderVo;
import com.hsu.service_ucenter.entity.RegisterVo;
import com.hsu.service_ucenter.entity.UcenterMember;
import com.hsu.service_ucenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-13
 */
@RestController
@RequestMapping("/ucenter/member")
public class UcenterMemberController {
    @Resource
    private UcenterMemberService memberService;

    // 登录
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember member) {
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    // 注册
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    // 根据token获取用户的信息
    @GetMapping("/userInfo")
    public R getUserInfoByToken(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(id);
        return R.ok().data("userInfo", member);
    }

    // 根据用户ID查询用户信息Order
    @PostMapping("/getUserMemberInfoOrder/{userId}")
    public UcenterMemberOrderVo getUserMemberInfoOrder(@PathVariable String userId) {
        UcenterMember member = memberService.getById(userId);
        UcenterMemberOrderVo ucenterMemberOrderVo = new UcenterMemberOrderVo();
        BeanUtils.copyProperties(member, ucenterMemberOrderVo);
        return ucenterMemberOrderVo;
    }

    // 根据日期查询注册人数信息
    @PostMapping("getRegisterInfo/{date}")
    public R getRegisterInfo(@PathVariable String date){
        int  num = memberService.getRegisterInfo(date);
        return R.ok().data("num", num);
    }

}

