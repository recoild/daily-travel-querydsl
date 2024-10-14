package com.fisa.dailytravel.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private Long id;
    private String title;
    private String content;
    private List<MultipartFile> imageFiles = new ArrayList<>();
    private List<String> hashtags = new ArrayList<>();
}
