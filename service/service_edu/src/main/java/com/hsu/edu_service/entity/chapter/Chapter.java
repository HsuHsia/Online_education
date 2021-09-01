package com.hsu.edu_service.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Chapter {
    private String id;
    private String title;

    private List<SubChapterVideo> children = new ArrayList<>();
}
