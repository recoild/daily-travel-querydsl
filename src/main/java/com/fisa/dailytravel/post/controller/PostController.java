package com.fisa.dailytravel.post.controller;

import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.post.dto.PostPagingRequest;
import com.fisa.dailytravel.post.dto.PostPagingResponse;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.dto.PostResponse;
import com.fisa.dailytravel.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/v1/post")
    public ApiResponse<String> createPost(@ModelAttribute PostRequest postRequest, JwtAuthenticationToken principal) throws IOException {
        String uuid = principal.getName();
        return ApiResponse.ok(postService.savePost(uuid, postRequest));
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
