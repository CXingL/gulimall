package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.vo.CommentVo;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-09-08
 */
@Api(tags = "课程评论")
@RestController
@RequestMapping("/eduservice/edu-comment")
public class EduCommentController {

    @Autowired
    EduCommentService eduCommentService;

    @GetMapping("/list/{courseId}/{page}/{limit}")
    public R commentList(@PathVariable Long courseId,
                         @PathVariable Long page,
                         @PathVariable Long limit) {
        Page<EduComment> commentPage = new Page<>(page, limit);
        eduCommentService.page(commentPage, new QueryWrapper<EduComment>().eq("course_id", courseId).orderByDesc("gmt_create"));
        long total = commentPage.getTotal();
        List<EduComment> records = commentPage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("/sendComment")
    public R sendComment(@RequestBody CommentVo commentVo) {
        EduComment eduComment = new EduComment();
        // 根据 token 获得评论人信息并保存
        BeanUtils.copyProperties(commentVo, eduComment);
        eduCommentService.save(eduComment);
        return R.ok();
    }

    @DeleteMapping("/delete/{commentId}")
    public R deleteComment(@PathVariable String commentId) {
        eduCommentService.removeById(commentId);
        return R.ok();
    }
}

