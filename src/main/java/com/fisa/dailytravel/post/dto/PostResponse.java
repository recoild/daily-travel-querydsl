package com.fisa.dailytravel.post.dto;

import com.fisa.dailytravel.post.models.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private Double latitude;
    private Double longitude;
    private LocalDate date;

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getUser().getNickname())
                .placeName(post.getPlaceName())
                .likesCount(post.getLikesCount())
                .thumbnail(post.getThumbnail())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .date(post.getUpdatedAt() == null ? post.getCreatedAt() : post.getUpdatedAt())
                .build();
    }
}
