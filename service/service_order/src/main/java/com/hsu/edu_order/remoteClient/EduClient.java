package com.hsu.edu_order.remoteClient;

import com.hsu.commonutils.orderVo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-edu")
@Component
public interface EduClient {
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    CourseOrderVo getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
