package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-08-06
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoWithCourseId(String id) {
        List<EduChapter> chapters = baseMapper.selectList(new QueryWrapper<EduChapter>().eq("course_id", id));
        List<EduVideo> videos = eduVideoService.list(new QueryWrapper<EduVideo>().eq("course_id", id));

        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        for (EduChapter chapter :
                chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo video :
                    videos) {
                if (video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
            chapterVos.add(chapterVo);
        }

        return chapterVos;
    }

    @Override
    public boolean removeChapterById(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        int count = eduVideoService.count(wrapper.eq("chapter_id", chapterId));
        if(count > 0) {
            throw new GuliException(20001, "次章节下有小节，无法删除");
        }
        baseMapper.deleteById(chapterId);
        return true;
    }
}
