package com.fisa.dailytravel.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.post.models.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String authorProfileImagePath;
    private String placeName;
    private int likesCount;
    private String thumbnail;
    private List<String> images;
    private Double latitude;
    private Double longitude;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    private List<String> hashtags;
    private List<CommentResponse> comments;

    public static PostResponse of(Post post, List<String> imageFiles, List<String> hashtags, String authorProfileImagePath, List<CommentResponse> comments) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getUser().getNickname())
                .authorProfileImagePath(authorProfileImagePath)
                .placeName(post.getPlaceName())
                .likesCount(post.getLikesCount())
                .thumbnail(post.getThumbnail())
                .images(imageFiles)
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .hashtags(hashtags)
                .creationDate(post.getCreatedAt())
                .comments(comments)
                .build();
    }
}
