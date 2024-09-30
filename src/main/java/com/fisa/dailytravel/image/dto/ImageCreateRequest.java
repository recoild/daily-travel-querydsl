package com.fisa.dailytravel.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ImageCreateRequest {
    public Long postId;
    public String directoryName;
    public List<MultipartFile> imageFiles = new ArrayList<>();
}
