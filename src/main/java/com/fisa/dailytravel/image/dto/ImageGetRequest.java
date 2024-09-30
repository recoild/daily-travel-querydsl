package com.fisa.dailytravel.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ImageGetRequest {
    private Long postId;
}
