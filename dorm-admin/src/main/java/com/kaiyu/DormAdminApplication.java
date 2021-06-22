package com.kaiyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "com.kaiyu.dao")
public class DormAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormAdminApplication.class, args);
    }

}
