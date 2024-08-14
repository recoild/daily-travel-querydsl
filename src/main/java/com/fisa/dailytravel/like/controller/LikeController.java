package com.fisa.dailytravel.like.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.like.dto.LikeRequest;
import com.fisa.dailytravel.like.models.Like;
import com.fisa.dailytravel.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/v1/likes")
    public ApiResponse<Boolean> toggleLike(@RequestBody LikeRequest likeRequest, JwtAuthenticationToken principal) {
        return ApiResponse.ok(likeService.likeToggle(likeRequest.getPostId(), principal.getName()));
    }






}
