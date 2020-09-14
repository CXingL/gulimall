package com.atguigu;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

/**
 * @Author liuxing
 * @Date 2020/8/24 15:22
 * @Version 1.0
 */

public class AliyunVODTest {
    public static void main(String[] args) throws ClientException {
        DefaultAcsClient client = initVodClient("LTAI4FysshF3qmBormn7pYM7", "Kj98yzC6uEe9MwEuA6l85Xzq5IHQuy");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("ce63581d7c4d46d4b7264ca69cc7726d");

        GetPlayInfoResponse response = new GetPlayInfoResponse();
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfos = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo: playInfos){
            System.out.println(playInfo.getPlayURL());
        }
    }

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
