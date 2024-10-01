package com.fisa.dailytravel.comment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Builder
public class CommentResponse {
    private Long id; // 댓글 ID
//    private Long postId; // 댓글이 달린 게시글 ID
    private String content; // 댓글 내용
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt; // 댓글 생성 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // 댓글 수정 날짜
    private Long usersId; // 댓글 작성자 ID
    private String nickname; // 댓글 작성자 닉네임
    private String profileImagePath; // 댓글 작성자 프로필 이미지 경로

    @QueryProjection
    public CommentResponse(Long id, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Long usersId, String nickname, String profileImagePath) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.usersId = usersId;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
    }
}
