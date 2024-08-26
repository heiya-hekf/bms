package com.hekf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.hekf.web.*.mapper"})
public class BMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(BMSApplication.class, args);
    }


}
