package com.fisa.dailytravel.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PostPreviewResponse {
    private Long id;
    private String title;
    private String nickname;
    private String profileImagePath;
    private String content;
    private int likeCount;
    //    private List<String> imageFiles;
    private String thumbnail;
    private List<String> hashtags = new ArrayList<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
//
//    public static PostPreviewResponse of(Post post, List<String> imageFiles, List<String> hashtags) {
//        return PostPreviewResponse.builder()
//                .id(post.getId())
//                .title(post.getTitle())
//                .author(post.getUser().getNickname())
//                .authorProfile(post.getUser().getProfileImagePath())
//                .likeCount(post.getLikesCount())
//                .imageFiles(imageFiles)
//                .hashtags(hashtags)
//                .content(post.getContent())
//                .creationDate(post.getCreatedAt())
//                .build();
//    }

    @QueryProjection
    public PostPreviewResponse(Long id, String title, String nickname, String profileImagePath, String content, int likeCount, String thumbnail, LocalDateTime creationDate, List<String> hashtags) {
        this.id = id;
        this.title = title;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
        this.content = content;
        this.likeCount = likeCount;
        this.thumbnail = thumbnail;
        this.creationDate = creationDate;
        this.hashtags = hashtags;
    }
}
