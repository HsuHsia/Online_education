package com.hsu.edu_service.controller.userFront;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsu.commonutils.R;
import com.hsu.edu_service.entity.EduCourse;
import com.hsu.edu_service.entity.EduTeacher;
import com.hsu.edu_service.service.EduCourseService;
import com.hsu.edu_service.service.EduTeacherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/eduservice/userfront")
public class UserFrontController {
    @Resource
    private EduCourseService courseService;

    @Resource
    private EduTeacherService teacherService;

    @GetMapping("getHotCourseAndTeacher")
    public R index() {
        // 查出前8条热门课程
        List<EduCourse> courseList = courseService.getHotCourse();

        // 查出前4名讲师
        List<EduTeacher> teacherList = teacherService.getHotTeacher();
        return R.ok().data("courseList",courseList).data("teacherList", teacherList);
    }
}
