package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertisUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadAvatarFile(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertisUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertisUtils.KEY_ID;
        String accessKeySecret = ConstantPropertisUtils.KEY_SECRET;

        // 生成文件名：yyyy/MM/dd/filename
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String date = new DateTime().toString("yyyy/MM/dd");
        String name = date + "/" + uuid + file.getOriginalFilename();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ConstantPropertisUtils.BUCKET_NAME, name, inputStream);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // 关闭OSSClient.
            ossClient.shutdown();
        }

        // https://cxing-guli.oss-cn-chengdu.aliyuncs.com/bot.jpeg
        return "https://"+ConstantPropertisUtils.BUCKET_NAME+"."+endpoint+"/"+name;
    }
}
