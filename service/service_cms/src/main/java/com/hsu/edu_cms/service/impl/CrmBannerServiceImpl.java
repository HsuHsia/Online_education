package com.hsu.edu_cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsu.edu_cms.entity.CrmBanner;
import com.hsu.edu_cms.mapper.CrmBannerMapper;
import com.hsu.edu_cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-08
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        // select * from crm_banner ordered by desc limit 2
        wrapper.orderByDesc("id");
        // 用last方法在sql语句末尾拼接语句
        wrapper.last("limit 2");
        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);
        return bannerList;
    }
}
