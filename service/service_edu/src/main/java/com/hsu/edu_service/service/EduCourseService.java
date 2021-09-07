package com.hsu.edu_service.service;

import com.hsu.edu_service.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsu.edu_service.entity.vo.CourseInfoVo;
import com.hsu.edu_service.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程id查询课程的基本信息
    CourseInfoVo getCourseInfo(String courseId);

    void updateByCourseId(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String courseId);

    void removeCourseById(String courseId);
}
