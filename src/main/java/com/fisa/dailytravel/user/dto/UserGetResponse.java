package com.fisa.dailytravel.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGetResponse {
    private String email;
    private String nickname;
    private String profileImagePath;
    private PostPreviewResponse recentPost;
    private List<PostPreviewResponse> likedPosts;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Boolean isDeleted;
}
