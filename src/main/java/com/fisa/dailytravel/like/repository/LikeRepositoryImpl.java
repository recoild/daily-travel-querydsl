package com.fisa.dailytravel.like.repository;

import com.fisa.dailytravel.like.models.QLike;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.dto.QPostPreviewResponse;
import com.fisa.dailytravel.post.models.QHashtag;
import com.fisa.dailytravel.post.models.QImage;
import com.fisa.dailytravel.post.models.QPost;
import com.fisa.dailytravel.post.models.QPostHashtag;
import com.fisa.dailytravel.user.models.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.*;

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

        List<PostPreviewResponse> posts = queryFactory
                .from(post)
                .join(like).on(like.postId.eq(post.id))  // 좋아요로 연결된 게시글 조회
                .leftJoin(user).on(post.userId.eq(user.id))  // 게시글 작성자 정보
                .leftJoin(postHashtag).on(postHashtag.postId.eq(post.id))  // 게시글과 해시태그의 연결 테이블
                .leftJoin(hashtag).on(postHashtag.hashtagId.eq(hashtag.id))  // 해시태그 테이블
                .where(like.userId.eq(userId))  // 특정 사용자가 좋아요를 누른 게시글 필터링
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .transform(
                        groupBy(post.id).list(
                                new QPostPreviewResponse(
                                        post.id,
                                        post.title,
                                        user.nickname,  // 사용자 닉네임을 userId로 대체 (실제로는 User 엔티티로 처리 필요)
                                        user.profileImagePath,
                                        post.content,
                                        post.likesCount,
                                        post.thumbnail,
                                        post.createdAt,
                                        list(hashtag.hashtagName)  // 해시태그 목록을 그룹화하여 리스트로 만듦
                                )
                        )
                );

        Long totalCount = queryFactory.select(post.count())
                .from(post)
                .join(like).on(like.postId.eq(post.id))
                .where(like.userId.eq(userId))
                .fetchOne();

        Long finalTotalCount = Optional.ofNullable(totalCount).orElse(0L);
        return PageableExecutionUtils.getPage(posts, pageable, () -> finalTotalCount);
//
//        List<PostPreviewResponse> content  = queryFactory.select(new QPostPreviewResponse(
//                post.id,
//                post.title,
//                user.nickname,
//                user.profileImagePath,
//                post.content,
//                post.likesCount,
//                post.thumbnail,
//                post.createdAt
//        ))
//                .from(post)
//                .join(like).on(post.id.eq(like.postId))
//                .join(user).on(post.userId.eq(user.id))
//                .where(like.postId.eq(userId))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        // 2. 게시글 ID를 기준으로 해시태그 목록 조회
//        Map<Long, List<String>> hashtagsMap = queryFactory
//                .select(postHashtag.postId, hashtag.hashtagName)
//                .from(postHashtag)
//                .join(hashtag).on(postHashtag.hashtagId.eq(hashtag.id))
//                .where(postHashtag.postId.in(
//                        content.stream().map(PostPreviewResponse::getId).collect(Collectors.toList())
//                ))
//                .fetch()
//                .stream()
//                .collect(Collectors.groupingBy(
//                        tuple -> tuple.get(postHashtag.postId),
//                        Collectors.mapping(tuple -> tuple.get(hashtag.hashtagName), Collectors.toList())
//                ));
//
//        // 3. 해시태그 리스트를 각 게시글에 할당
//        content.forEach(postPreview -> postPreview.setHashtags(hashtagsMap.get(postPreview.getId())));
//
//        // 4. 전체 개수 쿼리 (페이지네이션을 위해 필요)
//        Long totalCount = queryFactory
//                .select(post.count())
//                .from(post)
//                .join(like).on(post.id.eq(like.postId))
//                .join(user).on(post.userId.eq(user.id))
//                .where(like.userId.eq(userId))
//                .fetchOne();
//
//        Long finalTotalCount = Optional.ofNullable(totalCount).orElse(0L);
//
//        return PageableExecutionUtils.getPage(content, pageable, () -> finalTotalCount);
    }
}
