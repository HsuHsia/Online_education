package com.hsu.edu_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsu.edu_service.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-10
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getHotTeacher();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> page);
}
