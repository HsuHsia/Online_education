package com.hsu.edu_service.controller;


import com.hsu.commonutils.R;
import com.hsu.edu_service.entity.vo.CourseInfoVo;
import com.hsu.edu_service.entity.vo.CoursePublishVo;
import com.hsu.edu_service.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

}

