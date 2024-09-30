package com.fisa.dailytravel.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    void saveImages(Long postId, String userNickname, List<MultipartFile> imageFiles) throws Exception;

    void updateImages() throws Exception;

    void deleteImages() throws Exception;

    void getImages() throws Exception;
}
