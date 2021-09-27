package com.hsu.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsu.edu_service.entity.EduCourse;
import com.hsu.edu_service.entity.EduCourseDescription;
import com.hsu.edu_service.entity.EduTeacher;
import com.hsu.edu_service.entity.vo.CourseInfoVo;
import com.hsu.edu_service.entity.vo.CoursePublishVo;
import com.hsu.edu_service.entity.vo.frontVo.CourseFrontVo;
import com.hsu.edu_service.entity.vo.frontVo.CourseWebVo;
import com.hsu.edu_service.mapper.EduCourseMapper;
import com.hsu.edu_service.service.EduChapterService;
import com.hsu.edu_service.service.EduCourseDescriptionService;
import com.hsu.edu_service.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsu.edu_service.service.EduVideoService;
import com.hsu.servicebase.exceptionhandler.SummerException;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private EduChapterService eduChapterService;

    @Resource
    private EduVideoService eduVideoService;

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

    @Override
    public void removeCourseById(String courseId) {
        // 删除小节和视频
        eduVideoService.deleteVideoByCourseId(courseId);

        // 删除章节
        eduChapterService.deleteChapterByCourseId(courseId);

        // 删除课程描述
        eduCourseDescriptionService.removeById(courseId);

        // 删除课程
        int delete = baseMapper.deleteById(courseId);
        if(delete == 0) {
            throw new SummerException(20001, "删除课程失败");
        }
    }

    @Cacheable(value = "hotCourse", key = "'hotCourseList'")
    @Override
    public List<EduCourse> getHotCourse() {
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> courseList = baseMapper.selectList(wrapperCourse);
        return courseList;
    }


    //=======================================================前端用户service============================================
    // 前端带分页的条件查询
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 是否有一级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }

        // 是否有二级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        // 是否有时间条件
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }

        // 是否有关注度条件
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        // 是否有价格条件
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, wrapper);

        List<EduCourse> courses = coursePage.getRecords();
        long currentPage = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", courses);
        map.put("current", currentPage);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getFrontCourseInfo(String courseId) {
        CourseWebVo courseInfo = baseMapper.getFrontCourseInfo(courseId);
        return courseInfo;
    }
}
