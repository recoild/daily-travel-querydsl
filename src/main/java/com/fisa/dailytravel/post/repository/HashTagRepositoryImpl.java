package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.Hashtag;
import com.fisa.dailytravel.post.models.QHashtag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class HashTagRepositoryImpl implements HashTagRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findExistingHashtags(List<String> hashtags) {
        QHashtag hashtag= QHashtag.hashtag;

        return queryFactory
                .select(hashtag.hashtagName)
                .from(hashtag)
                .where(hashtag.hashtagName.in(hashtags))
                .fetch();
    }
}
