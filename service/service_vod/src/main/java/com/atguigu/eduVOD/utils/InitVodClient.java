package com.atguigu.eduVOD.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @Author liuxing
 * @Date 2020/8/26 13:51
 * @Version 1.0
 */
public class InitVodClient {

//    public DefaultAcsClient InitVodClient() throws ClientException {
//        return initVodClient("LTAI4FysshF3qmBormn7pYM7", "Kj98yzC6uEe9MwEuA6l85Xzq5IHQuy");
//    }
    public static DefaultAcsClient initVodClient() throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, "LTAI4FysshF3qmBormn7pYM7", "Kj98yzC6uEe9MwEuA6l85Xzq5IHQuy");
        return new DefaultAcsClient(profile);
    }
}
