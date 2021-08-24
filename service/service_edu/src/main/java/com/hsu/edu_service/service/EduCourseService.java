package com.hsu.edu_service.service;

import com.hsu.edu_service.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsu.edu_service.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
public interface EduCourseService extends IService<EduCourse> {

    void saveCourseInfo(CourseInfoVo courseInfoVo);
}
