package com.atguigu.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author liuxing
 * @Date 2020/9/10 14:46
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.atguigu.staservice.mapper")
@EnableSwagger2
@EnableScheduling
@ComponentScan(basePackages = {"com.atguigu"})
public class StatisticApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticApplication.class, args);
    }
}
