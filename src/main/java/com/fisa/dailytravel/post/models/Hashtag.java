package com.fisa.dailytravel.post.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hashtag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hashtag_seq")
    @SequenceGenerator(name = "hashtag_seq", sequenceName = "hashtag_seq", allocationSize = 1)
    @Column(name = "hashtag_id")
    private Long id;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostHashtag> postHashtags = new HashSet<>();
}
