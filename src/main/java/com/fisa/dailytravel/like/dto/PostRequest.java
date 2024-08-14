package com.fisa.dailytravel.like.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostRequest {
    private String postTitle;
    private String postContent;
    private String placeName;
    private int likesCount;
    private String thumbnail;
    private Double latitude;
    private Double longitude;



}
