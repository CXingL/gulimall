package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-08-05
 */
@Api(tags = "课程分类相关")
@RestController
@RequestMapping("/eduservice/edu-subject")
public class EduSubjectController {
    @Autowired
    EduSubjectService eduSubjectService;

    @ApiOperation("上传课程分类 excel")
    @PostMapping("uploadSubject")
    public R uploadSubject(MultipartFile file){
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok();
    }

    @ApiOperation("获取全部课程分类")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> subjectList = eduSubjectService.getAllSubject();
        return R.ok().data("items", subjectList);
    }
}

