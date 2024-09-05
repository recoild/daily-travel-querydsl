package com.fisa.dailytravel.post.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "posts")
public class PostDoc {
    @Id
    @Field(name = "post_id", type = FieldType.Keyword)
    private Long id;

    @Field(name = "users_id", type = FieldType.Keyword)
    private String userId;

    @Field(name = "latitude", type = FieldType.Double)
    private Double latitude;

    @Field(name = "thumbnail", type = FieldType.Text)
    private String thumbnail;

    @Field(name = "likes_count", type = FieldType.Integer)
    private int likesCount;

    @Field(name = "created_at", type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @Field(name = "updated_at", type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    @Field(name = "post_content", type = FieldType.Keyword)
    private String postContent;

    @Field(name = "longitude", type = FieldType.Double)
    private Double longitude;

    @Field(name = "post_title", type = FieldType.Text)
    private String title;

    @Field(name = "place_name", type = FieldType.Text)
    private String placeName;

}
