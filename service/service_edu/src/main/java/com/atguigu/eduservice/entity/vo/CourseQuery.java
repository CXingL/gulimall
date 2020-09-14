package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CourseQuery {
    @ApiModelProperty("课程名称")
    private String title;
    @ApiModelProperty("课程状态")
    private String status;
}
