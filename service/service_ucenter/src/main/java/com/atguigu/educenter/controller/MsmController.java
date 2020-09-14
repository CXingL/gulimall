package com.atguigu.educenter.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educenter.service.MsmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author liuxing
 * @Date 2020/9/3 10:02
 * @Version 1.0
 */
@Api(tags = "短信相关")
@RestController
@RequestMapping("/msm")
public class MsmController {

    @Resource
    private MsmService msmService;

    @ApiOperation("发送短信")
    @GetMapping("/sendMsg/{phone}")
    public R sendMsg(@PathVariable Long phone) {
        this.msmService.sendMsg(phone);
        return R.ok();
    }
}
