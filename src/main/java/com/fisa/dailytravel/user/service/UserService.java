package com.fisa.dailytravel.user.service;

import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserUpdateRequest;
import com.fisa.dailytravel.user.dto.UserUpdateResponse;
import com.fisa.dailytravel.user.models.User;

import java.io.IOException;

public interface UserService {
    public void signin(UserCreateRequest userCreateRequest) throws Exception;

    User getUser(String uuid) throws Exception;

    UserUpdateResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest) throws IOException;
}
