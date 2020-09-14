package com.atguigu.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author liuxing
 * @Date 2020/9/14 11:36
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.atguigu.aclservice.mapper")
@ComponentScan("com.atguigu")
@EnableDiscoveryClient
@EnableSwagger2
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }
}
