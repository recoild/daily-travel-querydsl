package com.fisa.dailytravel.like.models;

import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "likes_seq")
    @SequenceGenerator(name = "likes_seq", sequenceName = "likes_seq", allocationSize = 1)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;
}
