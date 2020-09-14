package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;
import javafx.beans.binding.ObjectExpression;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author cxing
 * @since 2020-09-09
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNative(Long orderNo);

    Map<String, String> queryPayStatus(Long orderNo);

    void updatePayStatus(Map<String, String> map);
}
