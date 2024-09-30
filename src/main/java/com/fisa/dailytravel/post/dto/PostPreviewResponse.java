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
    private String plcaeName;
    private int likeCount;
    private String thumbnail;
    private List<String> hashtags = new ArrayList<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @QueryProjection
    public PostPreviewResponse(Long id, String title, String nickname, String profileImagePath, String content, String plcaeName, int likeCount, String thumbnail, LocalDateTime creationDate, List<String> hashtags) {
        this.id = id;
        this.title = title;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
        this.content = content;
        this.plcaeName = plcaeName;
        this.likeCount = likeCount;
        this.thumbnail = thumbnail;
        this.creationDate = creationDate;
        this.hashtags = hashtags;
    }
}
