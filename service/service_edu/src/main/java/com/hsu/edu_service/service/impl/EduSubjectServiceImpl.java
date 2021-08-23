package com.hsu.edu_service.service.impl;

import com.alibaba.excel.EasyExcel;
import com.hsu.edu_service.entity.EduSubject;
import com.hsu.edu_service.entity.excel.SubjectData;
import com.hsu.edu_service.listener.SubjectExcelListener;
import com.hsu.edu_service.mapper.EduSubjectMapper;
import com.hsu.edu_service.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
