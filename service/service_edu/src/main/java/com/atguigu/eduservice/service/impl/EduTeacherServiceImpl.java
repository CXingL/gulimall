package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-07-22
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Cacheable(key = "'teacher'", value = "index")
    @Override
    public List<EduTeacher> listIndex(QueryWrapper<EduTeacher> teacherWrapper) {
        return baseMapper.selectList(teacherWrapper);
    }

    @Override
    public Map<String, Object> pageFrontTeacherList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("level");

        baseMapper.selectPage(teacherPage, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", teacherPage.getRecords());
        map.put("hasNext", teacherPage.hasNext());
        map.put("total", teacherPage.getTotal());
        return map;
    }
}
