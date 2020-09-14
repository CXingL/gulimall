package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.dto.CourseFrontInfoDto;
import com.atguigu.eduservice.entity.vo.CourseFrontInfoVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cxing
 * @since 2020-08-06
 */
public interface EduCourseService extends IService<EduCourse> {

    void saveCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishInfo(String courseId);

    void publishCourse(String courseId);

    Page<EduCourse> pageEduCourseCondition(long current, long limit, CourseQuery courseQuery);

    void removeByCourseId(String courseId);

    List<EduCourse> listIndex(QueryWrapper<EduCourse> courseWrapper);

    List<EduCourse> getCourseListByTeacherId(Long teacherId);

    Map<String, Object> pageFrontCourseList(Page<EduCourse> coursePage, CourseFrontInfoVo courseFrontInfoVo);

    CourseFrontInfoDto getCourseBaseInfo(Long courseId);
}
