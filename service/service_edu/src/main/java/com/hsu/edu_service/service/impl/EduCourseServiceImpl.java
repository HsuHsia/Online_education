package com.hsu.edu_service.service.impl;

import com.hsu.edu_service.entity.EduCourse;
import com.hsu.edu_service.entity.EduCourseDescription;
import com.hsu.edu_service.entity.vo.CourseInfoVo;
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
    public void saveCourseInfo(CourseInfoVo courseInfoVo) {
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
    }
}
