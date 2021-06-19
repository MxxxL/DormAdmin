package com.kaiyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableTransactionManagement
@EnableCaching
@MapperScan(basePackages = "com.kaiyu.dao")
public class DormAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormAdminApplication.class, args);
    }

}
