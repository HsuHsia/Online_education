package com.hsu.edu_cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsu.commonutils.R;
import com.hsu.edu_cms.entity.CrmBanner;
import com.hsu.edu_cms.service.CrmBannerService;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/educms/bannerAdmin")
public class CrmAdminBannerController {
    @Resource
    private CrmBannerService bannerService;

    // 分页查询
    @GetMapping("pageBanner/{currentPage}/{pageLimit}")
    public R pageBanner(@PathVariable long currentPage, @PathVariable long pageLimit) {
        Page<CrmBanner> page = new Page<>(currentPage, pageLimit);
        bannerService.page(page);
        long total = page.getTotal();
        List<CrmBanner> bannerList = page.getRecords();
        return R.ok().data("total", total).data("items", bannerList);
    }

    // 添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    // 根据id查询banner
    @GetMapping("getBannerById/{bannerId}")
    public R getBannerById(@PathVariable String bannerId){
        CrmBanner banner = bannerService.getById(bannerId);
        return R.ok().data("banner", banner);
    }

    // 修改banner
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    // 删除
    @DeleteMapping("deleteBanner/{bannerId}")
    public R deleteBannerById(@PathVariable String bannerId) {
        boolean flag = bannerService.removeById(bannerId);
        return flag ? R.ok() : R.error();
    }

}

