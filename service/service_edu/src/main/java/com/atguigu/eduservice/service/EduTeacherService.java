package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author cxing
 * @since 2020-07-22
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> listIndex(QueryWrapper<EduTeacher> teacherWrapper);

    Map<String, Object> pageFrontTeacherList(Page<EduTeacher> teacherPage);
}
