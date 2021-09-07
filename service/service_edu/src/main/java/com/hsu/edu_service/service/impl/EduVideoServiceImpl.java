package com.hsu.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsu.edu_service.client.VodClient;
import com.hsu.edu_service.entity.EduVideo;
import com.hsu.edu_service.mapper.EduVideoMapper;
import com.hsu.edu_service.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
//  注入vod_client
    @Resource
    private VodClient vodClient;

    @Override
    public void deleteVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
//      只查video_source_id这一列
        wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapper);

//      将List<EduVideo> 变成List<String>
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : videoList) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }

        if (videoIds.size() > 0) {
            vodClient.deleteMultiVideo(videoIds);
        }

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        baseMapper.delete(wrapperVideo);
    }
}
