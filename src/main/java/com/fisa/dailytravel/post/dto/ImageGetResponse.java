package com.fisa.dailytravel.post.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ImageGetResponse {
    private List<String> imagePaths;
}
