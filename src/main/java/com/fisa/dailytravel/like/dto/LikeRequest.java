package com.fisa.dailytravel.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class LikeRequest {
    private Long postId;
    private int page;
    private int count;
}
