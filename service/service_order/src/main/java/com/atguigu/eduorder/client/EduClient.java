package com.atguigu.eduorder.client;

import com.atguigu.servicebase.entity.CourseOrderDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author liuxing
 * @Date 2020/9/9 11:11
 * @Version 1.0
 */
@FeignClient(value = "service-edu")
@Component
public interface EduClient {

    @ApiOperation("订单需要的课程信息")
    @GetMapping("/eduservice/course/getCourseOrder/{courseId}")
    public CourseOrderDto getCourseOrder(@PathVariable("courseId") Long courseId);
}
