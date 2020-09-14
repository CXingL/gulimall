package com.atguigu.eduorder.client;

import com.atguigu.servicebase.entity.MemberOrderDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author liuxing
 * @Date 2020/9/9 11:11
 * @Version 1.0
 */
@FeignClient(value = "service-ucenter")
@Component
public interface UcenterClient {

    @ApiOperation("订单需要的会员信息")
    @GetMapping("/educenter/member/getMemberForOrder/{memberId}")
    public MemberOrderDto getMemberForOrder(@PathVariable("memberId") String memberId);
}
