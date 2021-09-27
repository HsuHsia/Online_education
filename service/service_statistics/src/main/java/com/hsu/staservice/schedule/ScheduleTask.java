package com.hsu.staservice.schedule;

import com.hsu.staservice.service.StatisticsDailyService;
import com.hsu.staservice.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {
    private StatisticsDailyService statisticsDailyService;
    @Scheduled(cron = "0 0 2 * * ?")
    public void task1() {
        statisticsDailyService.getRegisterInfo(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
