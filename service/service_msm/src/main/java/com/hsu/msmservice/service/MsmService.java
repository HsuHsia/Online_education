package com.hsu.msmservice.service;

import java.util.Map;

public interface MsmService {
    boolean sendVerification(String verificationCode, String phoneNumber);
}
