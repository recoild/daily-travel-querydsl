package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.Hashtag;
import com.fisa.dailytravel.post.models.PostHashtag;
import com.fisa.dailytravel.post.models.QPostHashtag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostHashtagRepositoryImpl implements PostHashtagRespositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostHashtag> findAllByHashtags(List<Hashtag> hashtags) {
        QPostHashtag postHashtag = QPostHashtag.postHashtag;

        List<Long> hashtagIds = hashtags.stream().map(Hashtag::getId).collect(Collectors.toList());

        return queryFactory
                .selectFrom(postHashtag)
                .where(postHashtag.hashtagId.in(hashtagIds))
                .fetch();
    }
}
