package com.fisa.dailytravel.user.service;

import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserCreateResponse;
import com.fisa.dailytravel.user.dto.UserFeedResponse;
import com.fisa.dailytravel.user.dto.UserInfoResponse;
import com.fisa.dailytravel.user.dto.UserUpdateRequest;
import com.fisa.dailytravel.user.dto.UserUpdateResponse;

import java.io.IOException;

public interface UserService {
    UserCreateResponse signin(UserCreateRequest userCreateRequest) throws Exception;

    UserInfoResponse getUserInfo(String uuid) throws Exception;

    UserFeedResponse getUserFeed(String uuid) throws Exception;

    UserUpdateResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest) throws IOException;

    void deleteUser(String uuid) throws Exception;
}
