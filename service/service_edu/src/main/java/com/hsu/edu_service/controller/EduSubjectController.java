package com.hsu.edu_service.controller;


import com.hsu.commonutils.R;

import com.hsu.edu_service.entity.subject.OneSubject;
import com.hsu.edu_service.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-19
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Resource
    private EduSubjectService subjectService;

    @PostMapping ("/addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<OneSubject> subjects = subjectService.getAllOneTwoSubject();
        return R.ok().data("data", subjects);
    }
}

