package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.models.QPost;
import com.fisa.dailytravel.user.dto.UserGetResponse;
import com.fisa.dailytravel.user.models.QUser;
import com.fisa.dailytravel.user.models.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByUuid(String uuid) {
        QUser user = QUser.user;
        User result = queryFactory.selectFrom(user)
                .where(user.uuid.eq(uuid))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<UserGetResponse> getUserWithFeed(String uuid) {
        QUser user = QUser.user;
        QPost post = QPost.post;
        return Optional.empty();
    }
}
