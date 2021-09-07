package com.hsu.edu_service.client;

import com.hsu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient{
    @Override
    public R deleteAlyVideo(String videoId) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteMultiVideo(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
