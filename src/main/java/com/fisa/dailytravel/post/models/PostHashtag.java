package com.fisa.dailytravel.post.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_hashtag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class PostHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_hashtag_seq")
    @SequenceGenerator(name = "post_hashtag_seq", sequenceName = "post_hashtag_seq", allocationSize = 1)
    @Column(name = "post_hashtag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;
}
