package com.hsu.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

// exclude = DataSourceAutoConfiguration.class 不加载数据库信息
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.hsu")
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }
}
