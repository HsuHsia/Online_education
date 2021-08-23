package com.hsu.edu_service.controller;


import com.hsu.commonutils.R;
import com.hsu.edu_service.service.EduSubjectService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-19
 */
@RestController
@RequestMapping("/edu_service/subject")
@CrossOrigin
public class EduSubjectController {
    @Resource
    private EduSubjectService subjectService;

    @PostMapping ("addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }
}

