package com.fisa.dailytravel.like.repository;

import com.fisa.dailytravel.like.models.QLike;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.models.QPost;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<List<PostPreviewResponse>> findFavoritePostsByUserId(Long userId, Pageable pageable) {
        QPost post = QPost.post;
        QLike like = QLike.like;

        List<PostPreviewResponse> posts = queryFactory.select(new QPostPreviewResponse(
                post.id, post.title, post.content, post.likesCount, post.createdAt,
        ))

        Long total = queryFactory.select(post.count())
                .from(post)
                .join(like).on(post.id.eq(like.postId))
                .where(like.postId.eq(userId))
                .fetchOne();

        Long totalCount = Optional.ofNullable(total).orElse(0L);

    }
}
