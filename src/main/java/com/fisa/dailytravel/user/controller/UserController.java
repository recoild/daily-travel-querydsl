package com.fisa.dailytravel.user.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.user.dto.*;
import com.fisa.dailytravel.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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
        userService.signin(userCreateRequest);

        UserCreateResponse response = new UserCreateResponse("User created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/v1/user")
    public ApiResponse<UserGetResponse> getUser(JwtAuthenticationToken principal) throws Exception {
        String uuid = principal.getName();

        UserGetResponse user = userService.getUser(uuid);
        if (user == null) {
            return ApiResponse.error(null);
        }

        return ApiResponse.ok(user);
    }

    @PutMapping("/v1/user")
    public ApiResponse<UserUpdateResponse> updateUser(@ModelAttribute UserUpdateRequest userUpdateRequest, JwtAuthenticationToken principal) throws IOException {
        String uuid = principal.getName();

        UserUpdateResponse userUpdateResponse = userService.updateUser(uuid, userUpdateRequest);

        return ApiResponse.ok(userUpdateResponse);
    }
}
