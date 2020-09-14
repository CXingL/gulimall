package com.atguigu.eduservice.entity.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class TwoSubject {
    @ApiModelProperty("课程 id")
    private String id;
    @ApiModelProperty("课程名称")
    private String title;
}
