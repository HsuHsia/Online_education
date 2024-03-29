package com.hsu.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.hsu.staservice.mapper")
@ComponentScan({"com.hsu"})
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling           // 开启定时任务注解
public class StatisticsApplicatiom {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplicatiom.class, args);
    }
}
