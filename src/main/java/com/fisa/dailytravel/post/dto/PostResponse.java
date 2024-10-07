package com.fisa.dailytravel.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@Builder
public class PostResponse {
    //    private Long id;
    private String title;
    private String content;
    private String nickname;
    private String profileImagePath;
    private String placeName;
    private int likesCount;
    //    private String thumbnail;
    private List<String> images;
    //    private Double latitude;
//    private Double longitude;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    private List<String> hashtags;
    private List<CommentResponse> comments;
    private boolean mine;

    @QueryProjection
    public PostResponse(String title,
                        String content,
                        String nickname,
                        String profileImagePath,
                        String placeName,
                        int likesCount,
                        List<String> images,
                        LocalDateTime creationDate,
                        List<String> hashtags,
                        List<CommentResponse> comments,
                        boolean mine) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
        this.placeName = placeName;
        this.likesCount = likesCount;
        this.images = images;
        this.creationDate = creationDate;
        this.hashtags = hashtags;
        this.comments = comments;
        this.mine = mine;
    }

//    public static PostResponse of(Post post, List<String> imageFiles, List<String> hashtags, String authorProfileImagePath, List<CommentResponse> comments, boolean mine) {
//        return PostResponse.builder()
//                .id(post.getId())
//                .title(post.getTitle())
//                .content(post.getContent())
////                .author(post.getUser().getNickname())
//                .authorProfileImagePath(authorProfileImagePath)
//                .placeName(post.getPlaceName())
//                .likesCount(post.getLikesCount())
//                .thumbnail(post.getThumbnail())
//                .images(imageFiles)
////                .latitude(post.getLatitude())
////                .longitude(post.getLongitude())
//                .hashtags(hashtags)
//                .creationDate(post.getCreatedAt())
//                .comments(comments)
//                .mine(mine)
//                .build();
//    }
}
