package com.hsu.edu_service.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EduConfig {
//    分页插件
    @Bean public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
