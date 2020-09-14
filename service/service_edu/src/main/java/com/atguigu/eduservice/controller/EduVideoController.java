package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-08-06
 */
@Api(tags = "课程小节相关")
@RestController
@RequestMapping("/eduservice/edu-video")
public class EduVideoController {

    @Autowired
    EduVideoService videoService;

    @Autowired
    VodClient vodClient;

    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    @ApiOperation("获取小节")
    @GetMapping("getVideo/{videoId}")
    public R getVideo(@PathVariable String videoId) {
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video", video);
    }

    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

    @ApiOperation("删除小节")
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        EduVideo video = videoService.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeVideo(Long.valueOf(videoSourceId));
        }

        boolean flag = videoService.removeById(videoId);
        if (flag) {
            return R.ok();
        }
        return R.error().message("删除小节失败");
    }
}

