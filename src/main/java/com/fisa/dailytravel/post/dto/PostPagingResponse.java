package com.fisa.dailytravel.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostPagingResponse {
    private int page;
    private List<PostPreviewResponse> postPreviewResponses;
    private boolean isEnd;
}
