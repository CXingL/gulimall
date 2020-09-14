package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author cxing
 * @since 2020-09-09
 */
public interface OrderService extends IService<Order> {

    String createOrders(Long courseId, String memberId);
}
