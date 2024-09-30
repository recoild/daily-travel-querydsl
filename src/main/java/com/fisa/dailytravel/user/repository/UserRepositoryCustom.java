package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.user.dto.UserGetResponse;
import com.fisa.dailytravel.user.models.User;

import java.util.Optional;

public interface UserRepositoryCustom{
    Optional<User> findByUuid(String uuid);
    Optional<UserGetResponse> getUserWithFeed(String uuid);
}
