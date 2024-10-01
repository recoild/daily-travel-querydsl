package com.fisa.dailytravel.image.service;

import com.fisa.dailytravel.image.dto.ImageCreateRequest;
import com.fisa.dailytravel.image.dto.ImageGetRequest;
import com.fisa.dailytravel.image.dto.ImageGetResponse;

import java.util.List;

public interface ImageService {
    void saveImages(ImageCreateRequest imageCreateRequest) throws Exception;

    ImageGetResponse getImages(ImageGetRequest imageGetRequest) throws Exception;

    void updateImages(ImageCreateRequest imageCreateRequest) throws Exception;

    void deleteImages(Long postId) throws Exception;

}
