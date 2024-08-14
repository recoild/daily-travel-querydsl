package com.fisa.dailytravel.post.dto;

import com.fisa.dailytravel.post.models.Post;
import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostResponse {
    private String title;
    private String content;
    private String author;
    private String placeName;
    private int likesCount;
    private String thumbnail;
    private List<String> images;
    private Double latitude;
    private Double longitude;
    private String creationDate;

    public static PostResponse of(Post post, List<String> imageFiles) {
        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getUser().getNickname())
                .placeName(post.getPlaceName())
                .likesCount(post.getLikesCount())
                .thumbnail(post.getThumbnail())
                .images(imageFiles)
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .creationDate(post.getUpdatedAt() == null ? post.getCreatedAt().toString() : post.getUpdatedAt().toString())
                .build();
    }
}
