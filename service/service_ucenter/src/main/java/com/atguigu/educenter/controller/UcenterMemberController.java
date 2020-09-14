package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.servicebase.entity.MemberOrderDto;
import com.atguigu.educenter.entity.vo.LoginVO;
import com.atguigu.educenter.entity.vo.RegistVO;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-09-03
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("/regist")
    public R regist(@RequestBody RegistVO registVO) {
        String token = ucenterMemberService.saveMember(registVO);
        return R.ok().data("token", token);
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO) {
        String token = ucenterMemberService.login(loginVO);
        return R.ok().data("token", token);
    }

    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    @ApiOperation("订单需要的会员信息")
    @GetMapping("getMemberForOrder/{memberId}")
    public MemberOrderDto getMemberForOrder(@PathVariable Long memberId) {
        UcenterMember member = ucenterMemberService.getById(memberId);
        MemberOrderDto memberOrderDto = new MemberOrderDto();
        BeanUtils.copyProperties(member, memberOrderDto);
        return memberOrderDto;
    }

    @ApiOperation("查询某天注册人数")
    @GetMapping("getRegisterCounter/{day}")
    public R getRegisterCounter(@PathVariable String day) {
        Integer count = ucenterMemberService.getRegisterCounter(day);
        return R.ok().data("count", count);
    }

}

