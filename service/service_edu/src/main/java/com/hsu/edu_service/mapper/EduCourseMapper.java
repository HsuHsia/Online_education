package com.hsu.edu_service.mapper;

import com.hsu.edu_service.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsu.edu_service.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublishVoByCourseId(String courseId);
}
