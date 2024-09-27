package com.fisa.dailytravel.post.models;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"post", "hashtag"})
@Table(name = "post_hashtag")
@Entity
public class PostHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_hashtag_seq")
    @SequenceGenerator(name = "post_hashtag_seq", sequenceName = "post_hashtag_seq", allocationSize = 10)
    @Column(name = "post_hashtag_id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;
//
//    @ManyToOne
//    @JoinColumn(name = "hashtag_id", nullable = false)
//    private Hashtag hashtag;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "hashtag_id")
    private Long hashtagId;
}
