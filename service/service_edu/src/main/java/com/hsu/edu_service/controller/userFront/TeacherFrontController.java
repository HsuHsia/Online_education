package com.hsu.edu_service.controller.userFront;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsu.commonutils.R;
import com.hsu.edu_service.entity.EduCourse;
import com.hsu.edu_service.entity.EduTeacher;
import com.hsu.edu_service.service.EduCourseService;
import com.hsu.edu_service.service.EduTeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
    @Resource
    private EduTeacherService teacherService;

    @Resource
    private EduCourseService courseService;

    // 用户前端分页查询讲师
    @GetMapping("getTeacherList/{currentPage}/{limitPage}")
    public R getTeacherFrontList(@PathVariable long currentPage, @PathVariable long limitPage) {
        Page<EduTeacher> page = new Page<>(currentPage, limitPage);
        Map<String, Object> map = teacherService.getTeacherFrontList(page);
        return R.ok().data("data",map);
    }


    // 根据讲师id查询讲师信息和讲师所讲的课程
    @GetMapping("getTeacherInfoAndCourseById/{teacherId}")
    public R getTeacherInfoAndCourseById(@PathVariable String teacherId) {
        // 根据讲师id查询讲师信息
        EduTeacher teacher = teacherService.getById(teacherId);

        // 根据讲师id查询讲师所讲的课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }
}