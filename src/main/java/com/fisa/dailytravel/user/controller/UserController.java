package com.fisa.dailytravel.user.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserCreateResponse;
import com.fisa.dailytravel.user.dto.UserGetResponse;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/v1/user")
    public ApiResponse<UserCreateResponse> signin(JwtAuthenticationToken principal) throws Exception {
        String email = principal.getTokenAttributes().get("email").toString();
        String picture = principal.getTokenAttributes().get("picture").toString();

        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail(email);
        userCreateRequest.setUuid(principal.getName());
        userCreateRequest.setPicture(picture);
        userService.signin(userCreateRequest);

        return ApiResponse.created(new UserCreateResponse("User created successfully"));
    }

    @GetMapping("/v1/user")
    public ApiResponse<UserGetResponse> getUser(JwtAuthenticationToken principal) {
        String uuid = principal.getName();
        User user = userService.getUser(uuid);
        if (user == null) {
            return ApiResponse.error(null);
        }
        UserGetResponse userGetResponse = UserGetResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImagePath(user.getProfileImagePath())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .isDeleted(user.getIsDeleted())
                .build();

        return ApiResponse.ok(userGetResponse);
    }
}
