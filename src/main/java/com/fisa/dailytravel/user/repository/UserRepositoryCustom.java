package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.user.dto.UserFeedResponse;
import com.fisa.dailytravel.user.models.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByUuid(String uuid);

    Optional<UserFeedResponse> getUserWithFeed(String uuid);
}
