package com.fisa.dailytravel.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserGetResponse {
    private String email;
    private String nickname;
    private String profileImagePath;
    private List<PostPreviewResponse> recentPosts;
    private List<PostPreviewResponse> likedPosts;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Boolean isDeleted;

    @QueryProjection
    public UserGetResponse(String email, String nickname, String profileImagePath, List<PostPreviewResponse> recentPosts, List<PostPreviewResponse> likedPosts, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isDeleted) {
        this.email = email;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
        this.recentPosts = recentPosts;
        this.likedPosts = likedPosts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }
}
