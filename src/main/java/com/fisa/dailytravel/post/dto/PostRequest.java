package com.fisa.dailytravel.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRequest {
    private Long id;
    private String title;
    private String content;
    private List<MultipartFile> imageFiles;
    private String placeName;
    private List<String> hashtags;
    private double latitude;
    private double longitude;
}
