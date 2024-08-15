package com.fisa.dailytravel.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private Long id; // 댓글이 달린 게시글 ID
    private String content; // 댓글 내용

}
