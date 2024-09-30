package com.fisa.dailytravel.like.models;

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

@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "likes_seq")
    @SequenceGenerator(name = "likes_seq", sequenceName = "likes_seq", allocationSize = 1)
    @Column(name = "likes_id")
    private Long id;

    //    @ManyToOne
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;
//
//    @ManyToOne
//    @JoinColumn(name = "users_id", nullable = false)
//    private User user;
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "users_id")
    private Long userId;
}
