package com.fisa.dailytravel.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class ImageGetResponse {
    private List<String> imagePaths;
}
