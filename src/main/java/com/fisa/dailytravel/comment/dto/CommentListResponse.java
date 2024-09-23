package com.fisa.dailytravel.comment.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentListResponse {
    private List<CommentResponse> comments;
}
