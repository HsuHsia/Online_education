package com.hsu.edu_service.controller;


import com.hsu.commonutils.R;
import com.hsu.edu_service.entity.vo.CourseInfoVo;
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
@Api(tags = "课程管理123333")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Resource
    private EduCourseService eduCourseService;

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok();
    }


}

