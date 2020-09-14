package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.RandomUtil;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.MsmService;
import com.atguigu.servicebase.exceptionhandler.exception.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author liuxing
 * @Date 2020/9/3 10:04
 * @Version 1.0
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendMsg(Long phoneNum) {
        String code = redisTemplate.opsForValue().get(String.valueOf(phoneNum));
        if (!StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "验证码已发送，请稍后再试");
        }
        code = RandomUtil.getFourBitRandom();
        //阿里云发送短信
        redisTemplate.opsForValue().set(String.valueOf(phoneNum), code, 1, TimeUnit.MINUTES);
    }
}
