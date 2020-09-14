package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-08-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        List<OneSubject> subjects = new ArrayList<>();
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        List<EduSubject> oneSubjects = this.list(wrapper.eq("parent_id", 0));
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        List<EduSubject> twoSubjects = this.list(wrapper2.ne("parent_id", 0));
        System.out.println(twoSubjects);
        for (EduSubject subject :
                oneSubjects) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject);
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for (EduSubject twoSubject :
                    twoSubjects) {
                if (twoSubject.getParentId().equals(subject.getId())) {
                    TwoSubject twoSubject1 = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject, twoSubject1);
                    twoSubjectList.add(twoSubject1);
                }
            }
            oneSubject.setTwoSubjectList(twoSubjectList);
            subjects.add(oneSubject);
        }
        return subjects;

//        List<OneSubject> subjects = new ArrayList<>();
//        // 获取一级分类列表
//        List<EduSubject> oneSubjectList = baseMapper.selectList(new QueryWrapper<EduSubject>().eq("parent_id", 0));
//        for (EduSubject oneSubject :
//                oneSubjectList) {
//            OneSubject one = new OneSubject();
//            BeanUtils.copyProperties(oneSubject, one);
//            // 获取该一级分类的子分类列表
//            ArrayList<TwoSubject> twoSubjects = new ArrayList<>();
//            List<EduSubject> twoSubjectList = baseMapper.selectList(new QueryWrapper<EduSubject>().eq("parent_id", oneSubject.getId()));
//            for (EduSubject twoSubject :
//                    twoSubjectList) {
//                TwoSubject two = new TwoSubject();
//                BeanUtils.copyProperties(twoSubject, two);
//                twoSubjects.add(two);
//            }
//            one.setTwoSubjectList(twoSubjects);
//            subjects.add(one);
//        }
//        return subjects;
    }
}
