package com.hsu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uplaodAliyunVideo(MultipartFile file);

    boolean deleteAlyVideoById(String videoId);

    void deleteMultiVideo(List videoIdList);
}
