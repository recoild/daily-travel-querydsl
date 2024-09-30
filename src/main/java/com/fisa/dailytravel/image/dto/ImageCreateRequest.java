package com.fisa.dailytravel.image.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageCreateRequest {
    private Long postId;
    private Long userId;
    private List<MultipartFile> imageFiles;
}
