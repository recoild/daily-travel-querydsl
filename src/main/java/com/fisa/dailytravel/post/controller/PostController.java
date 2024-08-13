package com.fisa.dailytravel.post.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/v1/post")
    public ApiResponse<String> createPost(@RequestBody PostRequest postRequest, JwtAuthenticationToken principal) {
        String uuid = principal.getName();
        return ApiResponse.ok(postService.savePost(uuid, postRequest));
    }
}
