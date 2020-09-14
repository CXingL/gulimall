package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author liuxing
 * @Date 2020/9/8 11:10
 * @Version 1.0
 */
@RestController
@RequestMapping("eduService/teacherFront")
public class TeacherFrontController {

    @Resource
    EduTeacherService eduTeacherService;

    @Resource
    EduCourseService eduCourseService;

    @GetMapping("getFrontTeacherList/{page}/{limit}")
    public R getFrontTeacherList(@PathVariable Long page,
                                 @PathVariable Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = eduTeacherService.pageFrontTeacherList(teacherPage);
        return R.ok().data(map);
    }

    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable Long teacherId) {
        EduTeacher teacher = eduTeacherService.getById(teacherId);
        List<EduCourse> courses = eduCourseService.getCourseListByTeacherId(teacherId);
        return R.ok().data("teacher", teacher).data("courseList", courses);
    }
}
