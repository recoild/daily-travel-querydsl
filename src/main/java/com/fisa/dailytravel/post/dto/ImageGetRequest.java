package com.fisa.dailytravel.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageGetRequest {
    private Long postId;
}
