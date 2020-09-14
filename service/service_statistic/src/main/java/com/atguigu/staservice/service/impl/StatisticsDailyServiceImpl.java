package com.atguigu.staservice.service.impl;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-09-10
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Override
    public void registerCount(String day) {
        //1. 依赖 2. 配置文件 3. 注解 4. 接口 5. 调用
        Integer count = 6;
        // 插入前删除旧数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);
        // 插入数据
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(count);
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated", type);
        wrapper.between("date_calculated", begin, end);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> numList = new ArrayList<>();
        for (StatisticsDaily statisticsDaily: statisticsDailies) {
            dateList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    numList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(statisticsDaily.getCourseNum());
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("numList", numList);
        return map;
    }
}
