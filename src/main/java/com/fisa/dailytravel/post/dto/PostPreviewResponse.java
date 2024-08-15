package com.fisa.dailytravel.post.dto;

import com.fisa.dailytravel.post.models.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostPreviewResponse {
    private Long id;
    private String title;
    private String author;
    private int likeCount;
    private List<String> imageFiles;
    private List<String> hashtags;
    private String creationDate;

    public static PostPreviewResponse of(Post post, List<String> imageFiles, List<String> hashtags) {
        return PostPreviewResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getUser().getNickname())
                .likeCount(post.getLikesCount())
                .imageFiles(imageFiles)
                .hashtags(hashtags)
                .creationDate(post.getUpdatedAt() == null ? post.getCreatedAt().toString() : post.getUpdatedAt().toString())
                .build();
    }
}
