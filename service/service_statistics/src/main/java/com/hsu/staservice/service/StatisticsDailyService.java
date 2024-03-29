package com.hsu.staservice.service;

import com.hsu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-19
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getRegisterInfo(String date);

    Map<String, Object> getShowData(String type, String beginDate, String endDate);
}
