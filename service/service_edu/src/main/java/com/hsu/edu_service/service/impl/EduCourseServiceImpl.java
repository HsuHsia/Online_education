package com.hsu.edu_service.service.impl;

import com.hsu.edu_service.entity.EduCourse;
import com.hsu.edu_service.entity.EduCourseDescription;
import com.hsu.edu_service.entity.vo.CourseInfoVo;
import com.hsu.edu_service.entity.vo.CoursePublishVo;
import com.hsu.edu_service.mapper.EduCourseMapper;
import com.hsu.edu_service.service.EduCourseDescriptionService;
import com.hsu.edu_service.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsu.servicebase.exceptionhandler.SummerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Resource
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int num = baseMapper.insert(eduCourse);

        if (num == 0) {
            throw new SummerException(20001, "添加课程失败");
        }

        // 得到 id 使得课程和描述的id相同
        String cid = eduCourse.getId();

        // 向课程简介表添加简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
//        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 查询课程表信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        // 查询描述表信息
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);

        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateByCourseId(CourseInfoVo courseInfoVo) {
        // 修改课程信息表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0){
            throw new SummerException(20001, "修改课程信息失败");
        }

        // 修改课程描述信息
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);
        eduCourseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        CoursePublishVo coursePublishVo= baseMapper.getCoursePublishVoByCourseId(courseId);
        return coursePublishVo;
    }
}
