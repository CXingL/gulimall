package com.atguigu.aclservice.controller;


import com.atguigu.aclservice.entity.User;
import com.atguigu.aclservice.service.UserService;
import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-09-14
 */
@Api(tags = "管理员相关")
@RestController
@RequestMapping("/aclservice/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("添加管理员")
    @PostMapping("add")
    public R addUser(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return R.ok();
    }

    @GetMapping("/logout")
    public R logout() {
        return R.ok();
    }

}

