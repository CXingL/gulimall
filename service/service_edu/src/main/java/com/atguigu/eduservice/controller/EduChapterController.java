package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.servicebase.exceptionhandler.exception.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
@Api(tags = "章节列表")
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;

    @ApiOperation("根据课程 id 获取章节列表")
    @GetMapping("getChapterVideo/{id}")
    public R getChapterVideo(@PathVariable("id") String id){
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoWithCourseId(id);
        if (chapterVoList == null || chapterVoList.size() == 0) {
            throw new GuliException(20001, "该课程不存在");
        }
        return R.ok().data("items", chapterVoList);
    }

    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @ApiOperation("查看章节")
    @GetMapping("getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("{chapterId}")
    public R removeChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.removeChapterById(chapterId);
        if (flag) {
            return R.ok();
        }
        return R.error().message("删除失败");
    }
}

