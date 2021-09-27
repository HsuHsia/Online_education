package com.hsu.msmservice.service.impl;

import com.hsu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;
import org.apache.commons.lang.StringUtils;
import java.rmi.ServerException;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    // 发送方短信的方法
    @Override
    public boolean sendVerification(String verificationCode, String phoneNumber) {

        if(StringUtils.isEmpty(phoneNumber)) return false;


        Credential cred = new Credential("AKIDJSKxJPEn56qKYVBajbCWZMs76pHSSBdo", "vay4tSkX4QWLtABwQdaGfnfGaeRxCArH");//自己的腾讯key

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

        SendSmsRequest req = new SendSmsRequest();


        String[] phoneNumberSet1 = {"+86"+phoneNumber};//电话
        String[] templateParamSet1 = {verificationCode};//验证码
        req.set("PhoneNumberSet",phoneNumberSet1);
        req.set("TemplateParamSet",templateParamSet1);
        req.set("TemplateID","1115506");//模板
        req.set("SmsSdkAppid","1400570192");//在添加应用看生成的实际SdkAppid
        req.set("Sign","SummernyHsu");//签名


        try {
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
