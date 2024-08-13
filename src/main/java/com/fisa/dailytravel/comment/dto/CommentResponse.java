package com.fisa.dailytravel.comment.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long id; // 댓글 ID
    private Long postId; // 댓글이 달린 게시글 ID
    private String content; // 댓글 내용
    private LocalDate createdAt; // 댓글 생성 날짜
    private LocalDate updatedAt; // 댓글 수정 날짜
    private Long usersId; // 댓글 작성자 ID
}
