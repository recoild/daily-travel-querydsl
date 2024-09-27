package com.fisa.dailytravel.post.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "post")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", sequenceName = "post_seq", allocationSize = 1)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Column(name = "post_content", nullable = false)
    private String content;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "likes_count")
    private int likesCount;

    @Column(name = "thumbnail")
    private String thumbnail;

//    @Column(name = "latitude", columnDefinition = "DECIMAL(9, 6)")
//    private Double latitude;
//
//    @Column(name = "longitude", columnDefinition = "DECIMAL(9, 6)")
//    private Double longitude;

    @Column(name = "created_at", insertable = false, updatable = false, nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

//    @ManyToOne
//    @JoinColumn(name = "users_id", nullable = false)
//    private User user;
//
//    @OneToMany(mappedBy = "post")
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "post")
//    private List<Image> images = new ArrayList<>();
//
//    @OneToMany(mappedBy = "post")
//    private List<Like> likes = new ArrayList<>();
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<PostHashtag> postHashtags = new HashSet<>();

    @Column(name = "users_id")
    private Long userId;
}
