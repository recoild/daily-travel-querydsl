package com.fisa.dailytravel.user.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserCreateResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/v1/user")
    public ApiResponse<UserCreateResponse> createUser(@RequestBody UserCreateRequest userCreateRequest, JwtAuthenticationToken principal) {
        return null;
//        return ApiResponse.ok(userService.createUser(userCreateRequest));
    }
}
