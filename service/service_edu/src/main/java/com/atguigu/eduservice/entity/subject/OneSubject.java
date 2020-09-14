package com.atguigu.eduservice.entity.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class OneSubject {
    @ApiModelProperty("课程 id")
    private String id;
    @ApiModelProperty("课程名称")
    private String title;
    @ApiModelProperty("二级课程分类列表")
    private List<TwoSubject> twoSubjectList;
}
