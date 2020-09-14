package com.atguigu.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author liuxing
 * @Date 2020/9/9 09:34
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.atguigu.eduorder.mapper")
@ComponentScan(basePackages = {"com.atguigu"})
@EnableSwagger2
//@EnableDiscoveryClient
//@EnableFeignClients
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
