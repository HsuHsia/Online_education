package com.hsu.edu_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.hsu.edu_service.mapper")
@ComponentScan(basePackages = {"com.hsu"}) // 组件扫描
@SpringBootApplication
@EnableDiscoveryClient   // nacos注册
@EnableFeignClients      // 服务调用注解
public class EduApplication {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled","false");
        SpringApplication.run(EduApplication.class, args);
    }
}
