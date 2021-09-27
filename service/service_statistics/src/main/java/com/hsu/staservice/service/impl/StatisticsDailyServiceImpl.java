package com.hsu.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsu.commonutils.R;
import com.hsu.staservice.entity.StatisticsDaily;
import com.hsu.staservice.mapper.StatisticsDailyMapper;
import com.hsu.staservice.remoteClient.UCenterClient;
import com.hsu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-19
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Resource
    private UCenterClient uCenterClient;

    @Override
    public void getRegisterInfo(String date) {
        //添加统计数据之前先删除表中日期重复的数据
        QueryWrapper<StatisticsDaily> wrapper= new QueryWrapper<>();
        wrapper.eq("date_calculated", date);
        baseMapper.delete(wrapper);

        R info = uCenterClient.getRegisterInfo(date);
        int registerNum = (Integer) info.getData().get("num");
        StatisticsDaily statistics = new StatisticsDaily();
        statistics.setRegisterNum(registerNum);         // 注册人数
        statistics.setDateCalculated(date);
        statistics.setCourseNum(RandomUtils.nextInt(100,200));
        statistics.setLoginNum(RandomUtils.nextInt(100,200));
        statistics.setVideoViewNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(statistics);
    }

    @Override
    public Map<String, Object> getShowData(String type, String beginDate, String endDate) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.ge("date_calculated", beginDate);
        wrapper.le("date_calculated", endDate);
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);

        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> typeDataList = new ArrayList<>();
        for (int i=0; i<statisticsDailies.size(); i++){
            StatisticsDaily daily = statisticsDailies.get(i);
            dateCalculatedList.add(daily.getDateCalculated());
            if (type.equals("register_num")){
                typeDataList.add(daily.getRegisterNum());
            }
            if (type.equals("login_num")){
                typeDataList.add(daily.getRegisterNum());
            }
            if (type.equals("video_view_num")){
                typeDataList.add(daily.getRegisterNum());
            }
            if (type.equals("course_num")){
                typeDataList.add(daily.getRegisterNum());
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("date_calculated", dateCalculatedList);
        map.put("numDateList",typeDataList);

        return map;
    }
}
