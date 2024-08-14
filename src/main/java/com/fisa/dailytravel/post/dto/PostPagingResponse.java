package com.fisa.dailytravel.post.dto;

import java.util.List;

public class PostPagingResponse {
    private int page;
    private List<PostPreviewResponse> postPreviewResponses;
    private boolean isEnd;
}
