package com.hsu.edu_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.hsu.edu_service.mapper")
@ComponentScan(basePackages = {"com.hsu"})
@SpringBootApplication
public class EduApplication {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled","false");
        SpringApplication.run(EduApplication.class, args);
    }
}
