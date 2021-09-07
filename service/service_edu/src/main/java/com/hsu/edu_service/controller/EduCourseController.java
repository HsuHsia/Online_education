package com.hsu.edu_service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsu.commonutils.R;
import com.hsu.edu_service.entity.EduCourse;
import com.hsu.edu_service.entity.vo.CourseInfoVo;
import com.hsu.edu_service.entity.vo.CoursePublishVo;
import com.hsu.edu_service.entity.vo.CourseQueryVo;
import com.hsu.edu_service.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Resource
    private EduCourseService eduCourseService;

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String cid = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", cid);
    }

    // 根据课程id查询课程的基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // 修改课程
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateByCourseId(courseInfoVo);
        return R.ok();
    }

    // 根据课程id查询要发布的课程信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = eduCourseService.getPublishCourseInfo(courseId);
        return R.ok().data("publishCourseInfo", coursePublishVo);
    }

//    更改课程状态 发布为normal 未发布为draft
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    @PostMapping("/getCourseCondition/{currentCoursePage}/{coursePageLimit}")
    public R getAllCourse(
            @ApiParam(name = "currentCoursePage",value = "当前页数",required = true)
            @PathVariable long currentCoursePage,
            @ApiParam(name = "coursePageLimit",value = "每页显示的条数",required = true)
            @PathVariable long coursePageLimit,
            @RequestBody CourseQueryVo courseQueryVo){

        // 查询条件
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        String courseName = courseQueryVo.getCourseName();
        String status = courseQueryVo.getStatus();
        if (!StringUtils.isEmpty(courseName)) wrapperCourse.like("title", courseName);
        if (!StringUtils.isEmpty(status)) wrapperCourse.eq("status", status);
        wrapperCourse.orderByDesc("gmt_create");

        // 分页查询
        Page<EduCourse> coursePage = new Page<>(currentCoursePage, coursePageLimit);
        eduCourseService.page(coursePage, wrapperCourse);
        long total = coursePage.getTotal();
        List<EduCourse> list = coursePage.getRecords();
        return R.ok().data("list",list).data("total", total);
    }

    @DeleteMapping("/deleteCourseById/{courseId}")
    public R deleteCourseById(@PathVariable String courseId) {
        eduCourseService.removeCourseById(courseId);
        return R.ok();
    }
}

