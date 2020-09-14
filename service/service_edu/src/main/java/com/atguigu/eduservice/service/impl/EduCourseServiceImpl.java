package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.course.CourseStatus;
import com.atguigu.eduservice.entity.dto.CourseFrontInfoDto;
import com.atguigu.eduservice.entity.vo.CourseFrontInfoVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-08-06
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduVideoService eduVideoService;

    @Override
    @Transactional
    public void saveCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0 ) {
            throw new GuliException(20001, "保存课程信息出错");
        }

        String cid = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
    }

    @Override
    @Transactional
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    @Transactional
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        baseMapper.updateById(eduCourse);
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String courseId) {
        return baseMapper.getCoursePublishInfo(courseId);
    }

    @Override
    public void publishCourse(String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(CourseStatus.NORMAL.getStatus());
        int flag = baseMapper.updateById(eduCourse);
        if (flag <= 0) {
            throw new GuliException(20001, "课程发布失败");
        }
    }

    @Override
    public Page<EduCourse> pageEduCourseCondition(long current, long limit, CourseQuery courseQuery) {
        Page<EduCourse> coursePage = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        this.page(coursePage, wrapper);
        return coursePage;
    }

    @Override
    public void removeByCourseId(String courseId) {
        eduVideoService.remove(new QueryWrapper<EduVideo>().eq("course_id", courseId));
        eduChapterService.remove(new QueryWrapper<EduChapter>().eq("course_id", courseId));
        eduCourseDescriptionService.removeById(courseId);
        int flag = baseMapper.deleteById(courseId);
        if (flag <= 0) {
            throw new GuliException(20001, "删除课程失败");
        }
    }

    @Cacheable(value = "index", key = "'course'")
    @Override
    public List<EduCourse> listIndex(QueryWrapper<EduCourse> courseWrapper) {
        return baseMapper.selectList(courseWrapper);
    }

    @Override
    public List<EduCourse> getCourseListByTeacherId(Long teacherId) {
        return baseMapper.selectList(new QueryWrapper<EduCourse>().eq("teacher_id", teacherId));
    }

    @Override
    public Map<String, Object> pageFrontCourseList(Page<EduCourse> coursePage, CourseFrontInfoVo courseFrontInfoVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontInfoVo.getTitle())) {
            wrapper.like("title", courseFrontInfoVo.getTitle());
        }
        if (!StringUtils.isEmpty(courseFrontInfoVo.getTeacherId())) {
            wrapper.eq("teacher_id", courseFrontInfoVo.getTeacherId());
        }
        if (!StringUtils.isEmpty(courseFrontInfoVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontInfoVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontInfoVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontInfoVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, wrapper);

        HashMap<String, Object> map = new HashMap<>();
        map.put("items", coursePage.getRecords());
        map.put("total", coursePage.getTotal());
        map.put("page", coursePage.getSize());
        map.put("hasNext", coursePage.hasNext());
        return map;
    }

    @Override
    public CourseFrontInfoDto getCourseBaseInfo(Long courseId) {
        return baseMapper.getCourseBaseInfo(courseId);
    }
}
