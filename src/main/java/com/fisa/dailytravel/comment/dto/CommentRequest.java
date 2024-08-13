package com.fisa.dailytravel.comment.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private Long id; // 댓글이 달린 게시글 ID
    private Long usersId; // 댓글 작성자 ID
    private String content; // 댓글 내용

}
