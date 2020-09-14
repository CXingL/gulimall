package com.atguigu.educenter.service;

import org.springframework.stereotype.Service;

/**
 * @Author liuxing
 * @Date 2020/9/3 10:03
 * @Version 1.0
 */
public interface MsmService {
    void sendMsg(Long phoneNum);
}
