package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @Author liuxing
 * @Date 2020/8/31 14:02
 * @Version 1.0
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeVideo(Long id) {
        return R.error().message("删除视频出错！");
    }
}
