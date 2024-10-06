package com.fisa.dailytravel.post.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class ImageCreateRequest {
    private Long postId;
    private String directoryName;
    private List<MultipartFile> imageFiles;
}
