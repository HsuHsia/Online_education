package com.hsu.service_ucenter.entity;

import lombok.Data;

@Data
public class RegisterVo {
    private String nickname;
    private String password;
    private String mobile;
    private String code;
}
