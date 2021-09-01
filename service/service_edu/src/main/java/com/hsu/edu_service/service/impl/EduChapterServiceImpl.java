package com.hsu.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsu.edu_service.entity.EduChapter;
import com.hsu.edu_service.entity.EduVideo;
import com.hsu.edu_service.entity.chapter.Chapter;
import com.hsu.edu_service.entity.chapter.SubChapterVideo;
import com.hsu.edu_service.mapper.EduChapterMapper;
import com.hsu.edu_service.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsu.edu_service.service.EduVideoService;
import com.hsu.servicebase.exceptionhandler.SummerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Resource
    private EduVideoService videoService;

    @Override
    public List<Chapter> getChapterVideoByCourseId(String courseId) {
        //查询所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("Course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(wrapperChapter);
        List<Chapter> finalChapterList = new ArrayList<>();

        // 查询所有小节课程
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("Course_id", courseId);
        List<EduVideo> videoList = videoService.list(wrapperVideo);


        for(int i=0; i<chapterList.size(); i++){
            Chapter chapter = new Chapter();
            EduChapter eduChapter = chapterList.get(i);
            BeanUtils.copyProperties(eduChapter, chapter);

            List<SubChapterVideo> finalVideoList = new ArrayList<>();

            for(int j=0; j<videoList.size(); j++) {
                EduVideo eduVideo = videoList.get(j);
                if (eduChapter.getId().equals(eduVideo.getChapterId())){
                    SubChapterVideo subChapterVideo = new SubChapterVideo();
                    BeanUtils.copyProperties(eduVideo, subChapterVideo);
                    finalVideoList.add(subChapterVideo);
                }
            }
            chapter.setChildren(finalVideoList);
            finalChapterList.add(chapter);
        }
        return finalChapterList;
    }

    @Override
    public boolean deleteChapterById(String chapterId) {
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("chapter_id", chapterId);
        int count = videoService.count(wrapperVideo);
        if (count > 0) {
            // 章节中存在小节，不允许删除章节
            throw new SummerException(20001,"章节中存在小节，不允许删除章节");
        } else {
            int delete = baseMapper.deleteById(chapterId);
            return delete>0;
        }
    }
}
