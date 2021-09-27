package com.hsu.edu_service.client;

import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrderClient{
    @Override
    public boolean isBuyCourse(String courseId, String userId) {
        return false;
    }
}
