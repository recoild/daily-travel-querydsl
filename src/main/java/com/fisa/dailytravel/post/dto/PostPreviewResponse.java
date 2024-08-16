package com.fisa.dailytravel.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fisa.dailytravel.post.models.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPreviewResponse {
    private Long id;
    private String title;
    private String author;
    private String authorProfile;
    private int likeCount;
    private List<String> imageFiles;
    private List<String> hashtags;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    public static PostPreviewResponse of(Post post, List<String> imageFiles, List<String> hashtags) {
        return PostPreviewResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getUser().getNickname())
                .authorProfile(post.getUser().getProfileImagePath())
                .likeCount(post.getLikesCount())
                .imageFiles(imageFiles)
                .hashtags(hashtags)
                .creationDate(post.getCreatedAt())
                .build();
    }
}
