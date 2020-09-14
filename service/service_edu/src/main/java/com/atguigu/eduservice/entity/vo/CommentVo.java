package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author liuxing
 * @Date 2020/9/8 15:54
 * @Version 1.0
 */
@Data
public class CommentVo {

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "评论内容")
    private String content;
}
