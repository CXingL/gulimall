package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.servicebase.entity.CourseOrderDto;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/eduservice/course")
@Api(tags = "课程管理相关")
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    @ApiOperation("所有课程列表")
    @GetMapping("findAll")
    public R findAll() {
        List<EduCourse> courses = eduCourseService.list(null);
        return R.ok().data("items", courses);
    }

    @ApiOperation("课程列表带查询、分页")
    @PostMapping("pageEduCourseCondition/{current}/{limit}")
    public R pageEduCourseCondition(@PathVariable long current,
                                    @PathVariable long limit,
                                    @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> coursePage = eduCourseService.pageEduCourseCondition(current, limit, courseQuery);
        long total = coursePage.getTotal();
        List<EduCourse> courses = coursePage.getRecords();
        return R.ok().data("total", total).data("rows", courses);
    }

    @ApiOperation("课程发布")
    @PostMapping("publishCourse")
    public R publishCourse(@RequestBody String courseId) {
        eduCourseService.publishCourse(courseId);
        return R.ok();
    }

    @ApiOperation("创建课程")
    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.saveCourse(courseInfoVo);
        return R.ok();
    }

    @GetMapping("getCourseInfo/{courseId}")
    @ApiOperation("根据 id 获取课程信息")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("info", courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    @ApiOperation("修改课程信息")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation("获取课程全部信息")
    @GetMapping("getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishInfo(courseId);
        return R.ok().data("course", coursePublishVo);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.removeByCourseId(courseId);
        return R.ok();
    }

    @ApiOperation("订单需要的课程信息")
    @GetMapping("getCourseOrder/{courseId}")
    public CourseOrderDto getCourseOrder(@PathVariable Long courseId) {
        EduCourse course = eduCourseService.getById(courseId);
        CourseOrderDto courseOrderDto = new CourseOrderDto();
        BeanUtils.copyProperties(course, courseOrderDto);
        return courseOrderDto;
    }
}

