package com.fisa.dailytravel.post.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.post.dto.*;
import com.fisa.dailytravel.post.fasade.RedissonLockPostFacade;
import com.fisa.dailytravel.post.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostServiceImpl postService;
    private final RedissonLockPostFacade redissonLockPostFacade;

    @PostMapping("/v1/post")
    public ApiResponse<String> createPost(@ModelAttribute PostRequest postRequest, JwtAuthenticationToken principal) throws IOException {
        String uuid = principal.getName();
        return ApiResponse.ok(redissonLockPostFacade.savePostUseRedisson(uuid, postRequest));
    }

    @GetMapping("/v1/post/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable("id") String postId, JwtAuthenticationToken principal) {
        String uuid = principal.getName();
        return ApiResponse.ok(postService.getPost(uuid, Long.valueOf(postId)));
    }

    @GetMapping("/v1/post")
    public ApiResponse<PostPagingResponse> getPosts(@ModelAttribute PostPagingRequest postPagingRequest, JwtAuthenticationToken principal) {
        String uuid = principal.getName();
        return ApiResponse.ok(postService.getAllPosts(uuid, postPagingRequest));
    }

    @GetMapping("/v1/post/search")
    public ApiResponse<PostPagingResponse> searchPostsV1(@ModelAttribute PostSearchPagingRequest postSearchPagingRequest, JwtAuthenticationToken principal) throws Exception {
        String uuid = principal.getName();
        return ApiResponse.ok(postService.searchPosts(uuid, postSearchPagingRequest));
    }

    @GetMapping("/v2/post/search")
    public ApiResponse<PostPagingResponse> searchPostsV2(@ModelAttribute PostSearchPagingRequest postSearchPagingRequest, JwtAuthenticationToken principal) throws Exception {
        String uuid = principal.getName();
        return ApiResponse.ok(postService.searchPostsWithES(uuid, postSearchPagingRequest));
    }

    @PutMapping("/v1/post")
    public ApiResponse<String> editPost(@ModelAttribute PostRequest postRequest, JwtAuthenticationToken principal) throws IOException {
        String uuid = principal.getName();
        return ApiResponse.ok(postService.modifyPost(uuid, postRequest));
    }

    @DeleteMapping("/v1/post")
    public ApiResponse<String> deletePost(@RequestBody PostRequest postRequest, JwtAuthenticationToken principal) {
        String uuid = principal.getName();
        return ApiResponse.ok(postService.deletePost(uuid, postRequest.getId()));
    }
}
