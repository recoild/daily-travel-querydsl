package com.fisa.dailytravel.user.controller.models;

import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.like.models.Like;
import com.fisa.dailytravel.post.models.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    @Column(name = "users_id")
    private Long id;

    @Column(nullable = false)
    private String uuid; //unique-user-id

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "profile_image_path")
    private String profileImagePath;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();
}
