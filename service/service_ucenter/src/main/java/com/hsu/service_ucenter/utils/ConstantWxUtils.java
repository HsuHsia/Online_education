package com.hsu.service_ucenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// InitializingBean是spring初始化的一个接口，在初始化spring时会执行afterPropertiesSet方法
@Component
public class ConstantWxUtils implements InitializingBean {
    @Value("${wx.open.app_id}")
    private String id;

    @Value("${wx.open.app_secret}")
    private String secret;

    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;
    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = id;
        WX_OPEN_APP_SECRET = secret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
