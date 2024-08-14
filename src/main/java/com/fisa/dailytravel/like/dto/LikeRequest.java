package com.fisa.dailytravel.like.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class LikeRequest {
    private Long postId;
}
