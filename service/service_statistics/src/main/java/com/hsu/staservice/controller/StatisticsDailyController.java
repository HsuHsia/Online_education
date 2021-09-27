package com.hsu.staservice.controller;

import com.hsu.commonutils.R;
import com.hsu.staservice.service.StatisticsDailyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-19
 */
@RestController
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {
    @Resource
    private StatisticsDailyService statisticsDailyService;

    @PostMapping("/getRegisterInfo/{date}")
    public R getRegisterInfo(@PathVariable String date) {
        statisticsDailyService.getRegisterInfo(date);
        return R.ok();
    }

    @GetMapping("/showStatistics/{type}/{beginDate}/{endDate}")
    public R showStatistics(@PathVariable String type, @PathVariable String beginDate, @PathVariable String endDate){
        Map<String, Object> map = statisticsDailyService.getShowData(type, beginDate, endDate);
        return R.ok().data(map);
    }
}

