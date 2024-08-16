package com.fisa.dailytravel.comment.dto;

import com.fisa.dailytravel.comment.models.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id; // 댓글 ID
    private Long postId; // 댓글이 달린 게시글 ID
    private String content; // 댓글 내용
    private String createdAt; // 댓글 생성 날짜
    private String updatedAt; // 댓글 수정 날짜
    private Long usersId; // 댓글 작성자 ID
    private String nickname;
    private String profileImagePath;

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt().toString())
                .updatedAt(comment.getUpdatedAt().toString())
                .usersId(comment.getUser().getId())
                .nickname(comment.getUser().getNickname())
                .profileImagePath(comment.getUser().getProfileImagePath())
                .build();
    }
}
