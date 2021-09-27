package com.hsu.edu_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsu.edu_service.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsu.edu_service.entity.vo.CourseInfoVo;
import com.hsu.edu_service.entity.vo.CoursePublishVo;
import com.hsu.edu_service.entity.vo.frontVo.CourseFrontVo;
import com.hsu.edu_service.entity.vo.frontVo.CourseWebVo;

import java.util.List;
import java.util.Map;

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

    List<EduCourse> getHotCourse();

    Map<String, Object> getCourseFrontList(Page<EduCourse> page, CourseFrontVo courseFrontVo);

    CourseWebVo getFrontCourseInfo(String courseId);
}
