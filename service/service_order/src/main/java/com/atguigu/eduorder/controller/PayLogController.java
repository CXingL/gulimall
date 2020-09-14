package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-09-09
 */
@RestController
@RequestMapping("/eduorder/pay-log")
public class PayLogController {

    @Autowired
    PayLogService payLogService;

    /**
     * 生成付款二维码
     */
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable Long orderNo) {
        Map<String, Object> map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    /**
     * 查询订单状态
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable Long orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("订单支付失败");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            // 更新订单状态，添加 pay log
            payLogService.updatePayStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(20005).message("支付中");
    }
}

