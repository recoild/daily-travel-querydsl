package com.fisa.dailytravel.user.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserCreateResponse;
import com.fisa.dailytravel.user.dto.UserFeedResponse;
import com.fisa.dailytravel.user.dto.UserInfoResponse;
import com.fisa.dailytravel.user.dto.UserUpdateRequest;
import com.fisa.dailytravel.user.dto.UserUpdateResponse;
import com.fisa.dailytravel.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/v1/user")
    public ResponseEntity<UserCreateResponse> signin(JwtAuthenticationToken principal) throws Exception {
        String email = principal.getTokenAttributes().get("email").toString();
        String picture = principal.getTokenAttributes().get("picture").toString();

        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail(email);
        userCreateRequest.setUuid(principal.getName());
        userCreateRequest.setPicture(picture);
        UserCreateResponse userCreateResponse = userService.signin(userCreateRequest);

        return ResponseEntity.status(userCreateResponse.getStatus()).body(userCreateResponse);
    }

    @GetMapping("/v1/user")
    public ResponseEntity<UserInfoResponse> getUser(JwtAuthenticationToken principal) throws Exception {
        String uuid = principal.getName();

        UserInfoResponse user = userService.getUserInfo(uuid);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/v1/user/feed")
    public ResponseEntity<UserFeedResponse> getUserFeed(JwtAuthenticationToken principal) throws Exception {
        String uuid = principal.getName();

        UserFeedResponse userFeed = userService.getUserFeed(uuid);

        return ResponseEntity.ok(userFeed);
    }

    @PutMapping("/v1/user")
    public ApiResponse<UserUpdateResponse> updateUser(@ModelAttribute UserUpdateRequest userUpdateRequest, JwtAuthenticationToken principal) throws IOException {
        String uuid = principal.getName();

        UserUpdateResponse userUpdateResponse = userService.updateUser(uuid, userUpdateRequest);

        return ApiResponse.ok(userUpdateResponse);
    }
}
