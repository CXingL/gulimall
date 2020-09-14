package com.atguigu.oss.controller;

import com.atguigu.oss.service.OssService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eduoss/fileOss")
public class OssControler {
    @Resource
    OssService ossService;

    @PostMapping
    public String uploadAvatarFile(MultipartFile file){
        return ossService.uploadAvatarFile(file);
    }
}
