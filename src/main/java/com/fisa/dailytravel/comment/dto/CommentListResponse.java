package com.fisa.dailytravel.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class CommentListResponse {
    private List<CommentResponse> comments;
}
