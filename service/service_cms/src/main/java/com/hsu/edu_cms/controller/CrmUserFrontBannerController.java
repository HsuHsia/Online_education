package com.hsu.edu_cms.controller;


import com.hsu.commonutils.R;
import com.hsu.edu_cms.entity.CrmBanner;
import com.hsu.edu_cms.service.CrmBannerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-08
 */
@RestController
@RequestMapping("/educms/bannerUserFront")
public class CrmUserFrontBannerController {
    @Resource
    private CrmBannerService bannerService;

    // 获取所有的banner
    @GetMapping("getAllBanner")
    public R getAllBanner(){
//        为了后面使用redis方便，这里重新写查询方法而不用service中的list方法
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("items",list);
    }
}

