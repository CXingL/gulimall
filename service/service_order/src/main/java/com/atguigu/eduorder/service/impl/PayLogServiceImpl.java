package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.entity.PayLog;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.mapper.PayLogMapper;
import com.atguigu.eduorder.service.PayLogService;
import com.atguigu.eduorder.utils.HttpClient;
import com.atguigu.eduorder.utils.HttpClientUtils;
import com.atguigu.eduorder.utils.WXPayUtil;
import com.atguigu.servicebase.exceptionhandler.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-09-09
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Resource
    OrderMapper orderMapper;

    @Override
    public Map<String, Object> createNative(Long orderNo) {
        try {
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderMapper.selectOne(wrapper);

            HashMap<String, String> m = new HashMap<>();
            //设置支付参数
            m.put("appid", "wx74862e0dfcf69954"); //微信id
            m.put("mch_id", "1558950191"); //商户号
            m.put("nonce_str", WXPayUtil.generateNonceStr()); //随机字符串
            m.put("body", order.getCourseTitle());  //信息
            m.put("out_trade_no", String.valueOf(orderNo)); //唯一标识订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+""); //订单价格
            m.put("spbill_create_ip", "127.0.0.1"); //op
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n"); //回调地址
            m.put("trade_type", "NATIVE"); //支付类型

            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            httpClient.setHttps(true);
            //发送请求
            httpClient.post();

            //得到发送请求返回结果 返回的是一个xml格式
            String content = httpClient.getContent();
            Map<String,String > resultMap = WXPayUtil.xmlToMap(content);
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code")); //返回二维码操作的状态码
            map.put("code_url", resultMap.get("code_url")); //二维码地址
            return map;
        } catch (Exception e) {
            throw new GuliException(20001, "生成二维码失败");
        }
    }

    @Override
    public Map<String, String> queryPayStatus(Long orderNo) {
        try{
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb")); //商户key
            client.setHttps(true);
            client.post();

            //3、得到参数 转成Map  返回
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            return resultMap;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updatePayStatus(Map<String, String> map) {
        String orderNo = map.get("out_trade_no");
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        if (order.getStatus() == 1) {return;}
        order.setStatus(1);
        orderMapper.updateById(order);

        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date()); //订单时间
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);
    }

}
