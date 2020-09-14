package com.atguigu.eduVOD.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.eduVOD.service.VodService;
import com.atguigu.eduVOD.utils.InitVodClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author liuxing
 * @Date 2020/8/26 13:53
 * @Version 1.0
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public void uploadVideo(MultipartFile video) {
        return;
    }

    @Override
    public void removeVideo(Long id) throws ClientException {
        DefaultAcsClient client = InitVodClient.initVodClient();
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(String.valueOf(id));
        try {
            client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
