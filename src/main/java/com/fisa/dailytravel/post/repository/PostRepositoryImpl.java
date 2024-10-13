package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.dto.QCommentResponse;
import com.fisa.dailytravel.comment.models.QComment;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.dto.PostResponse;
import com.fisa.dailytravel.post.dto.QPostPreviewResponse;
import com.fisa.dailytravel.post.models.Image;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.models.QHashtag;
import com.fisa.dailytravel.post.models.QImage;
import com.fisa.dailytravel.post.models.QPost;
import com.fisa.dailytravel.post.models.QPostHashtag;
import com.fisa.dailytravel.user.models.QUser;
import com.fisa.dailytravel.user.models.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.core.group.GroupBy.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    //게시글 상세정보 조회
    //게시글, 이미지, 해시태그, 댓글(처음 페이지) 조회
    @Override
    public PostResponse getPost(String uuid, Long postId) {
        QUser user = QUser.user;
        QPost post = QPost.post;
        QImage image = QImage.image;
        QHashtag hashtag = QHashtag.hashtag;
        QPostHashtag postHashtag = QPostHashtag.postHashtag;
        QComment comment = QComment.comment;

        User theUser = queryFactory.selectFrom(user)
                .where(user.uuid.eq(uuid))
                .fetchOne();

        Post thePost = queryFactory.selectFrom(post)
                .where(post.id.eq(postId))
                .fetchOne();

        List<Image> theImage = queryFactory.selectFrom(image)
                .where(image.postId.eq(postId))
                .fetch();

        List<String> theHashtags = queryFactory
                .select(hashtag.hashtagName)
                .from(hashtag)
                .leftJoin(postHashtag).on(hashtag.id.eq(postHashtag.hashtagId))
                .where(postHashtag.postId.eq(postId))
                .fetch();

        List<CommentResponse> theComments = queryFactory
                .select(new QCommentResponse(comment.id, comment.content, comment.createdAt, comment.updatedAt, user.uuid, user.nickname, user.profileImagePath))
                .from(comment)
                .join(user).on(user.id.eq(comment.userId))
                .where(comment.postId.eq(postId))
                .orderBy(comment.createdAt.asc())
                .limit(100)
                .fetch();

        return PostResponse.builder()
                .id(thePost.getId())
                .mine(thePost.getUserId().equals(theUser.getId()))
                .title(thePost.getTitle())
                .content(thePost.getContent())
                .nickname(theUser.getNickname())
                .profileImagePath(theUser.getProfileImagePath())
                .placeName(thePost.getPlaceName())
                .likesCount(thePost.getLikesCount())
                .creationDate(thePost.getCreatedAt())
                .images(theImage.stream().map(Image::getImagePath).toList())
                .hashtags(theHashtags)
                .comments(theComments)
                .build();
    }

    @Override
    public Page<PostPreviewResponse> getPosts(String uuid, Pageable pageRequest) {
        QPost post = QPost.post;
        QHashtag hashtag = QHashtag.hashtag;
        QPostHashtag postHashtag = QPostHashtag.postHashtag;
        QUser user = QUser.user;

        User me = queryFactory.selectFrom(user)
                .where(user.uuid.eq(uuid))
                .fetchOne();

        List<PostPreviewResponse> posts = queryFactory
                .from(post)
                .leftJoin(user).on(post.userId.eq(user.id))
                .leftJoin(postHashtag).on(postHashtag.postId.eq(post.id))
                .leftJoin(hashtag).on(postHashtag.hashtagId.eq(hashtag.id))
                .where(user.isDeleted.isFalse())
                .orderBy(post.createdAt.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .transform(
                        groupBy(post.id).list(
                                new QPostPreviewResponse(
                                        post.id,
                                        post.title,
                                        user.nickname,
                                        user.profileImagePath,
                                        post.content,
                                        post.placeName,
                                        post.likesCount,
                                        post.thumbnail,
                                        post.createdAt,
                                        list(hashtag.hashtagName),
                                        post.userId.eq(me.getId())
                                )
                        )
                );

        return new PageImpl<>(posts, pageRequest, posts.size());
    }
}
