package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.dto.CourseFrontInfoDto;
import com.atguigu.eduservice.entity.vo.CourseFrontInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author liuxing
 * @Date 2020/9/8 10:56
 * @Version 1.0
 */
@RestController
@RequestMapping("eduService/courseFront")
public class CourseFrontController {

    @Resource
    EduCourseService eduCourseService;

    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable Long page,
                                @PathVariable Long limit,
                                @RequestBody(required = false) CourseFrontInfoVo courseFrontInfoVo) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.pageFrontCourseList(coursePage, courseFrontInfoVo);
        return R.ok().data(map);
    }

    @GetMapping("/getBaseCourseInfo/{courseId}")
    public R getBaseCourseInfo(@PathVariable Long courseId) {
        CourseFrontInfoDto courseInfo = eduCourseService.getCourseBaseInfo(courseId);
        return R.ok().data("course", courseInfo);
    }
}
