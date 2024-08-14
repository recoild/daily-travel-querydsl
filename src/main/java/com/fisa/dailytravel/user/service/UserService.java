package com.fisa.dailytravel.user.service;

import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.models.User;

public interface UserService {
    public void signin(UserCreateRequest userCreateRequest) throws Exception;

    User getUser(String uuid);
}
