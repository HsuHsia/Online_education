package com.hsu.service_ucenter.controller;

import com.google.gson.Gson;
import com.hsu.commonutils.JwtUtils;
import com.hsu.service_ucenter.entity.UcenterMember;
import com.hsu.service_ucenter.service.UcenterMemberService;
import com.hsu.service_ucenter.utils.ConstantWxUtils;
import com.hsu.service_ucenter.utils.HttpClientUtils;
import com.hsu.servicebase.exceptionhandler.SummerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("api/ucenter/wx")
public class WxApiController {
    @Resource
    private UcenterMemberService memberService;


    @GetMapping("/callback")
    public String callback(String code, String state) {
        // 1 获取到扫码后返回的code

        // 2 用code访问微信提供的固定地址以获取访问凭证 access_token以及openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);

        // 2.1)这里使用httpclient发送请求访问微信提供的地址，获取访问凭证 access_token以及openid
        String accessTokenInfo = null;

        try{
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
        }catch (Exception e){
            throw new SummerException(20001, "获取access_token失败");
        }

        // 2.2) 将accessTokenInfo转换为map集合，根据key值得到accessTokenInfo中的access_token以及openid
        Gson gson = new Gson();
        HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
        String accessToken = accessTokenMap.get("access_token").toString();
        String openid = accessTokenMap.get("openid").toString();


        // 判断用户是否注册， 未注册则获取用户数据并将用户信息存到数据库
        UcenterMember member = memberService.getMemberByOpenId(openid);

        if (member == null) {
            // 未注册
            // 3 用access_token和openid访问微信提供的固定地址，得到扫码人的信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";

            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);

            // 3.1) 使用httpclient发送请求访问微信提供的地址，获取用户信息
            String userInfo = null;
            try{
                userInfo = HttpClientUtils.get(userInfoUrl);
            }catch (Exception e){
                throw new SummerException(20001, "获取用户信息失败");
            }
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = userInfoMap.get("nickname").toString();
            String headimgurl = userInfoMap.get("headimgurl").toString();

            UcenterMember addMember = new UcenterMember();
            addMember.setAvatar(headimgurl);
            addMember.setNickname(nickname);
            addMember.setOpenid(openid);
            memberService.save(addMember);
            // 使用jwt生成token字符串
            String token = JwtUtils.getJwtToken(addMember.getId(), addMember.getNickname());
            return "redirect:http://localhost:3000?token=" + token;
        }else {
            String tokenLogin = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token=" + tokenLogin;
        }
    }




    @GetMapping("/login")
    public String getWxQRCode(){

        // 微信开放平台授权baseUrl  其中%s相当于占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try{
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch (Exception e){
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "HsuHsia");

        return "redirect:" + url;
    }
}
