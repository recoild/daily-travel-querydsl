package com.fisa.dailytravel.like.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.like.dto.LikeRequest;
import com.fisa.dailytravel.like.dto.PostRequest;
import com.fisa.dailytravel.like.models.Like;
import com.fisa.dailytravel.like.service.LikeService;
import com.fisa.dailytravel.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/v1/likes")
    public ApiResponse<Boolean> toggleLike(@RequestBody LikeRequest likeRequest, JwtAuthenticationToken principal) {
        return ApiResponse.ok(likeService.likeToggle(likeRequest.getPostId(), principal.getName()));
    }


    @GetMapping("/v1/likes")
    public ApiResponse<List<PostResponse>> getFavoritePosts(@RequestBody LikeRequest likeRequest, JwtAuthenticationToken principal) {
        List<PostResponse> postResponses = likeService.favoritePosts(principal.getName(), likeRequest.getPage(), likeRequest.getCount());
        return ApiResponse.ok(postResponses);
    }






}
