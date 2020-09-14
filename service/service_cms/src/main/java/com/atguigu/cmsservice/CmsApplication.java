package com.atguigu.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author liuxing
 * @Date 2020/9/1 09:34
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.atguigu.cmsservice.mapper")
@CrossOrigin
@EnableSwagger2
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
