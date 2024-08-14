package com.fisa.dailytravel.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fisa.dailytravel.comment.models.Comment;
import lombok.*;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CommentResponse {

    private Long id; // 댓글 ID
    private Long postId; // 댓글이 달린 게시글 ID
    private String content; // 댓글 내용
    private String createdAt; // 댓글 생성 날짜
    private String updatedAt; // 댓글 수정 날짜
    private Long usersId; // 댓글 작성자 ID

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt().toString())
                .updatedAt(comment.getUpdatedAt().toString())
                .usersId(comment.getUser().getId())
                .build();
    }
}
