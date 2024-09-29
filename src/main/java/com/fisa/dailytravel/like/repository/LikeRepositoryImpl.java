package com.fisa.dailytravel.like.repository;

import com.fisa.dailytravel.like.models.QLike;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.dto.QPostPreviewResponse;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.models.QHashtag;
import com.fisa.dailytravel.post.models.QImage;
import com.fisa.dailytravel.post.models.QPost;
import com.fisa.dailytravel.post.models.QPostHashtag;
import com.fisa.dailytravel.user.models.QUser;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostPreviewResponse> findFavoritePostsByUserId(Long userId, Pageable pageable) {
        QPost post = QPost.post;
        QLike like = QLike.like;
        QUser user = QUser.user;
        QImage image = QImage.image;
        QPostHashtag postHashtag = QPostHashtag.postHashtag;
        QHashtag hashtag = QHashtag.hashtag;

        List<PostPreviewResponse> content  = queryFactory.select(new QPostPreviewResponse(
                post.id,
                post.title,
                user.nickname,
                user.profileImagePath,
                post.content,
                post.likesCount,
                post.thumbnail,
                post.createdAt
        ))
                .from(post)
                .join(like).on(post.id.eq(like.postId))
                .join(user).on(post.userId.eq(user.id))
                .where(like.postId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 2. 게시글 ID를 기준으로 해시태그 목록 조회
        Map<Long, List<String>> hashtagsMap = queryFactory
                .select(postHashtag.postId, hashtag.hashtagName)
                .from(postHashtag)
                .join(hashtag).on(postHashtag.hashtagId.eq(hashtag.id))
                .where(postHashtag.postId.in(
                        content.stream().map(PostPreviewResponse::getId).collect(Collectors.toList())
                ))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(postHashtag.postId),
                        Collectors.mapping(tuple -> tuple.get(hashtag.hashtagName), Collectors.toList())
                ));

        // 3. 해시태그 리스트를 각 게시글에 할당
        content.forEach(postPreview -> postPreview.setHashtags(hashtagsMap.get(postPreview.getId())));

        // 4. 전체 개수 쿼리 (페이지네이션을 위해 필요)
        Long totalCount = queryFactory
                .select(post.count())
                .from(post)
                .join(like).on(post.id.eq(like.postId))
                .join(user).on(post.userId.eq(user.id))
                .where(like.userId.eq(userId))
                .fetchOne();

        Long finalTotalCount = Optional.ofNullable(totalCount).orElse(0L);

        return PageableExecutionUtils.getPage(content, pageable, () -> finalTotalCount);
    }
}
