package com.hsu.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsu.edu_service.entity.EduTeacher;
import com.hsu.edu_service.mapper.EduTeacherMapper;
import com.hsu.edu_service.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-10
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //=======================================================后台管理员service===========================================
    @Cacheable(value = "hotTeacher", key = "'hotTeacherList'")
    @Override
    public List<EduTeacher> getHotTeacher() {
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = baseMapper.selectList(wrapperTeacher);
        return teacherList;
    }


    //=======================================================前端用户service============================================
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> page) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        baseMapper.selectPage(page, wrapper);

        List<EduTeacher> teacher = page.getRecords();
        long currentPage = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", teacher);
        map.put("current", currentPage);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
