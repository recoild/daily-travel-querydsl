package com.fisa.dailytravel.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRequest {
    private String title;
    private String content;
    private List<String> imageFiles;
    private String placeName;
    private List<String> hashtag;
    private double latitude;
    private double longitude;

    
}
