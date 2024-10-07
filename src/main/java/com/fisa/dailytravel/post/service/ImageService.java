package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.post.dto.ImageCreateRequest;
import com.fisa.dailytravel.post.dto.ImageGetRequest;
import com.fisa.dailytravel.post.dto.ImageGetResponse;

public interface ImageService {
    void saveImages(ImageCreateRequest imageCreateRequest) throws Exception;

    ImageGetResponse getImages(ImageGetRequest imageGetRequest) throws Exception;

    void updateImages(ImageCreateRequest imageCreateRequest) throws Exception;

    void deleteImages(Long postId) throws Exception;

}
