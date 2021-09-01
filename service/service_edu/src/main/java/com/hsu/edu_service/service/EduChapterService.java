package com.hsu.edu_service.service;

import com.hsu.edu_service.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsu.edu_service.entity.chapter.Chapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
public interface EduChapterService extends IService<EduChapter> {

    List<Chapter> getChapterVideoByCourseId(String courseId);

    boolean deleteChapterById(String chapterId);
}
