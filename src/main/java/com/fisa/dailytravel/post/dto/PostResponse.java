package com.fisa.dailytravel.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private String profileImagePath;
    private String placeName;
    private int likesCount;
    private List<String> images;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    private List<String> hashtags;
    private List<CommentResponse> comments;
    private boolean mine;

    public PostResponse(Long id,
                        String title,
                        String content,
                        String nickname,
                        String profileImagePath,
                        String placeName,
                        int likesCount,
                        LocalDateTime creationDate,
                        boolean mine) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
        this.placeName = placeName;
        this.likesCount = likesCount;
        this.creationDate = creationDate;
        this.mine = mine;
    }

    @QueryProjection
    public PostResponse(
            Long id,
            String title,
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
        this.id = id;
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
}