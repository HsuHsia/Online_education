package com.hsu.edu_service.controller.userFront;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsu.commonutils.JwtUtils;
import com.hsu.commonutils.R;
import com.hsu.commonutils.orderVo.CourseOrderVo;
import com.hsu.edu_service.client.OrderClient;
import com.hsu.edu_service.entity.EduCourse;
import com.hsu.edu_service.entity.chapter.Chapter;
import com.hsu.edu_service.entity.vo.frontVo.CourseFrontVo;
import com.hsu.edu_service.entity.vo.frontVo.CourseWebVo;
import com.hsu.edu_service.service.EduChapterService;
import com.hsu.edu_service.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Resource
    private EduCourseService courseService;

    @Resource
    private EduChapterService chapterService;

    @Resource
    private OrderClient orderClient;

    // 课程分页查询  用于列表显示
    @PostMapping("/getCourseList/{currentPage}/{pageLimit}")
    public R getCourseList(@PathVariable long currentPage, @PathVariable long pageLimit,
                           @RequestBody(required = false) CourseFrontVo courseFrontVo) {

        Page<EduCourse> page = new Page<>(currentPage, pageLimit);
        Map<String, Object> map =  courseService.getCourseFrontList(page, courseFrontVo);

        return R.ok().data(map);
    }

    // 根据课程id查询课程信息
    @GetMapping("getCourseInfoByCourseId/{courseId}")
    public R getCourseInfoByCourseId(@PathVariable String courseId, HttpServletRequest request) {
        CourseWebVo courseInfo = courseService.getFrontCourseInfo(courseId);

        // 根据课程id查询章节和小节
        List<Chapter> chapterVideoList= chapterService.getChapterVideoByCourseId(courseId);

        // 根据课程id和用户id查询课程是否被购买
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        boolean isBuyCourse = orderClient.isBuyCourse(courseId, userId);

        return R.ok().data("courseInfo", courseInfo).data("chapterAndVideoList", chapterVideoList).data("isBuyCourse", isBuyCourse);
    }

    // 根据课程ID返回课程信息Order
    @PostMapping("/getCourseInfoOrder/{courseId}")
    public CourseOrderVo getCourseInfoOrder(@PathVariable String courseId) {
        CourseWebVo courseInfo = courseService.getFrontCourseInfo(courseId);
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        BeanUtils.copyProperties(courseInfo, courseOrderVo);
        return courseOrderVo;
    }
}
