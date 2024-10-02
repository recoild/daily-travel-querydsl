package com.fisa.dailytravel.user.service;

import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserGetResponse;
import com.fisa.dailytravel.user.dto.UserUpdateRequest;
import com.fisa.dailytravel.user.dto.UserUpdateResponse;

import java.io.IOException;

public interface UserService {
    void signin(UserCreateRequest userCreateRequest) throws Exception;

    UserGetResponse getUserFeed(String uuid) throws Exception;

    UserUpdateResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest) throws IOException;

    void deleteUser(String uuid) throws Exception;
}
