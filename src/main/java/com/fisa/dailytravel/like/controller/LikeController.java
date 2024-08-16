package com.fisa.dailytravel.like.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.like.dto.LikeRequest;
import com.fisa.dailytravel.like.dto.LikeResponse;
import com.fisa.dailytravel.like.fasade.RedissonLockLikeFacade;
import com.fisa.dailytravel.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;
    private final RedissonLockLikeFacade redissonLockLikeFacade;

    @PostMapping("/v1/likes")
    public ApiResponse<Boolean> toggleLike(@RequestBody LikeRequest likeRequest, JwtAuthenticationToken principal) {
        return ApiResponse.ok(redissonLockLikeFacade.serviceToggle(likeRequest.getPostId(), principal.getName()));
    }

    @GetMapping("/v1/likes")
    public ApiResponse<LikeResponse> getFavoritePosts(@RequestParam("page") int page, @RequestParam("count") int count, JwtAuthenticationToken principal) {
//        return ApiResponse.ok(likeService.favoritePosts(principal.getName(), likeRequest.getPage(), likeRequest.getCount()));

        return ApiResponse.ok(likeService.favoritePosts(principal.getName(), page, count));
    }
}
