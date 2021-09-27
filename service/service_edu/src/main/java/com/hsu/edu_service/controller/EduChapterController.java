package com.hsu.edu_service.controller;


import com.hsu.commonutils.R;
import com.hsu.edu_service.entity.EduChapter;
import com.hsu.edu_service.entity.chapter.Chapter;
import com.hsu.edu_service.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author HsuHsia
 * @since 2021-08-24
 */
@Api(tags = "课程章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Resource
    private EduChapterService chapterService;

    // 根据课程id查询章节和小节
    @GetMapping("/getVideo/{courseId}")
    public R getChapterVideoByCourseId(@ApiParam(name="courseId", value = "课程id", required = true)
                                       @PathVariable String courseId) {
        List<Chapter> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("list", list);
    }


    // 添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    // 根据id查询章节
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    // 修改章节
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    // 根据章节id删除章节
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapterById(chapterId);
        return flag ? R.ok() : R.error();
    }

}

