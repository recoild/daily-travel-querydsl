package com.fisa.dailytravel.comment.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageRequest {
    private int page;
    private int count;
}
