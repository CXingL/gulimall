package com.atguigu.servicebase.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author liuxing
 * @Date 2020/9/9 10:33
 * @Version 1.0
 */
@Data
public class CourseOrderDto {

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;
}
