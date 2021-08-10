package com.hsu.edu_service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsu.commonutils.R;
import com.hsu.edu_service.entity.EduTeacher;
import com.hsu.edu_service.entity.vo.TeacherQuery;
import com.hsu.edu_service.service.EduTeacherService;
import com.hsu.servicebase.exceptionhandler.SummerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-10
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/edu_service/teacher")
public class EduTeacherController {
    @Resource
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll() {
        List<EduTeacher> list = teacherService.list(null);
        try {
            int i = 10 / 0;
        }catch (Exception e) {
            throw new SummerException(2001, "密码错误");
        }
        return R.ok().data("teacher", list);
    }


    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping ("{id}")
    public R removeTeacher(
            @ApiParam(name = "id",value = "讲师id",required = true) //提示信息
            @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }


    @ApiOperation(value = "分页查询")
    @GetMapping ("/pageTeacher/{currentPage}/{pageLimit}")
    public R pageListTeacher(
            @ApiParam(name = "currentPage",value = "当前的页数",required = true) //提示信息
            @PathVariable long currentPage,
            @ApiParam(name = "pageLimit",value = "每页显示的条数",required = true) //提示信息
            @PathVariable long pageLimit) {

        Page<EduTeacher> teacherPage = new Page<>(currentPage, pageLimit);
        teacherService.page(teacherPage);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }


    @ApiOperation(value = "条件组合查询")
    @PostMapping ("/pageTeacherCondition/{currentPage}/{pageLimit}")
    public R pageListTeacherCondition(
            @ApiParam(name = "currentPage",value = "当前的页数",required = true) //提示信息
            @PathVariable long currentPage,
            @ApiParam(name = "pageLimit",value = "每页显示的条数",required = true) //提示信息
            @PathVariable long pageLimit,
            @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> teacherPage = new Page<>(currentPage, pageLimit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String beginTime = teacherQuery.getBeginTime();
        String endTime = teacherQuery.getEndTime();
        if (!StringUtils.isEmpty(name)) wrapper.like("name", name);
        if (!StringUtils.isEmpty(level)) wrapper.eq("level", level);
        if (!StringUtils.isEmpty(beginTime)) wrapper.ge("gmt_create", beginTime);
        if (!StringUtils.isEmpty(endTime)) wrapper.le("gmt_create", endTime);
        teacherService.page(teacherPage, wrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }


    @ApiOperation(value = "添加教师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher) {
        boolean save = teacherService.save(teacher);
        return save ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @ApiOperation(value = "修改讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        return flag ? R.ok() : R.error();
    }
}

