package com.hsu.edu_service.service;

import com.hsu.edu_service.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsu.edu_service.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-19
 */
public interface EduSubjectService extends IService<EduSubject> {
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
