package com.atguigu.eduorder.service.impl;

import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.servicebase.entity.CourseOrderDto;
import com.atguigu.servicebase.entity.MemberOrderDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-09-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

//    @Resource
//    EduClient eduClient;
//
//    @Resource
//    UcenterClient ucenterClient;

    @Override
    public String createOrders(Long courseId, String memberId) {
//        CourseOrderDto courseOrder = eduClient.getCourseOrder(courseId);
//        MemberOrderDto memberForOrder = ucenterClient.getMemberForOrder(memberId);
        Order order = new Order();
//        BeanUtils.copyProperties(courseOrder, order);
//        BeanUtils.copyProperties(memberForOrder, order);
        order.setOrderNo(UUID.randomUUID().toString()); //订单号
        order.setCourseId(String.valueOf(courseId)); //课程id
//        order.setCourseTitle(courseOrder.getTitle());
//        order.setCourseCover(courseOrder.getCover());
//        order.setTeacherName(courseOrder.getTeacherName());
//        order.setTotalFee(courseOrder.getPrice());
        order.setMemberId(memberId);
//        order.setMobile(memberForOrder.getMobile());
//        order.setNickname(memberForOrder.getNickname());
        order.setStatus(0);  //0未支付 1已支付
        order.setPayType(1); //支付类型 微信支付1
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
