package com.atguigu.educenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author liuxing
 * @Date 2020/9/3 14:04
 * @Version 1.0
 */
@Data
public class RegistVO {

    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    private String code;
}
