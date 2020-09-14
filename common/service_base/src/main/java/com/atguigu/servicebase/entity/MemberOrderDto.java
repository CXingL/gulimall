package com.atguigu.servicebase.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author liuxing
 * @Date 2020/9/9 10:47
 * @Version 1.0
 */
@Data
public class MemberOrderDto {

    @ApiModelProperty(value = "会员id")
    private String id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickname;
}
