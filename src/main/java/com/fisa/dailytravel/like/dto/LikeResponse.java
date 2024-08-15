package com.fisa.dailytravel.like.dto;

import com.fisa.dailytravel.post.dto.PostPreviewResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class LikeResponse {
    private int page;
    private List<PostPreviewResponse> postPreviewResponses;
    private boolean isEnd;
}
