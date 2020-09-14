package com.atguigu.eduVOD.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author liuxing
 * @Date 2020/8/26 13:53
 * @Version 1.0
 */
public interface VodService {
    void uploadVideo(MultipartFile video);

    void removeVideo(Long id) throws ClientException;
}
