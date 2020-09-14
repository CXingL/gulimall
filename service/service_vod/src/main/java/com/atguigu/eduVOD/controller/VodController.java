package com.atguigu.eduVOD.controller;

import com.aliyuncs.exceptions.ClientException;
import com.atguigu.commonutils.R;
import com.atguigu.eduVOD.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author liuxing
 * @Date 2020/8/26 13:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    VodService vodService;

    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile video) {
        vodService.uploadVideo(video);
        return R.ok();
    }

    @DeleteMapping("delete/{id}")
    public R removeVideo(@PathVariable Long id){
        try {
            vodService.removeVideo(id);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return R.ok();
    }
}
