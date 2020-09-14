package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author liuxing
 * @Date 2020/9/10 14:53
 * @Version 1.0
 */
@Component
public class ScheduleTask {

    @Resource
    private StatisticsDailyService statisticsDailyService;

    @Scheduled(cron = "0 0 1 * * ? ")
    public void task() {
        statisticsDailyService.registerCount(DateUtils.formatDate(DateUtils.addDays(new Date(), -1)));
//        System.out.println(new Date());
    }
}
