package com.hsu.edu_service.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {
    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;


    @ApiModelProperty(value = "教师头衔 1高级讲师 2首席讲师")
    private Integer level;


    @ApiModelProperty(value = "查询开始时间", example = "2021-08-10 10:10:10")
    private String beginTime;


    @ApiModelProperty(value = "查询结束时间")
    private String endTime;
}
