package com.fisa.dailytravel.user.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserCreateResponse;
import com.fisa.dailytravel.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/v1/user")
    public ApiResponse<UserCreateResponse> createUser(JwtAuthenticationToken principal) {
        //get email from attribute
        String email = principal.getTokenAttributes().get("email").toString();

        //set email to userCreateRequest
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail(email);
        userCreateRequest.setUuid(principal.getName());
        userService.createUser(userCreateRequest);

        return ApiResponse.ok(new UserCreateResponse("User created successfully"));
//        return ApiResponse.ok(userService.createUser(userCreateRequest));
    }
}
