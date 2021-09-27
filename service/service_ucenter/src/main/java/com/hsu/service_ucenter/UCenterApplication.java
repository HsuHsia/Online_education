package com.hsu.service_ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.hsu"})
@SpringBootApplication
@MapperScan("com.hsu.service_ucenter.mapper")
@EnableDiscoveryClient      // 使得nacos能够发现模块，以用于远程调用
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class, args);
    }
}
