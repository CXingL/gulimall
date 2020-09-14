package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectData {
    @ExcelProperty(value = "一级课程", index = 0)
    private String oneSubjectName;
    @ExcelProperty(value = "二级课程", index = 1)
    private String twSubjectName;
}
