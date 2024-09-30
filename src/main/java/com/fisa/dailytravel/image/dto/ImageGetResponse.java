package com.fisa.dailytravel.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ImageGetResponse {
    private String imagePath;
}
