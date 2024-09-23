package com.fisa.dailytravel.post.controller;

import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.global.dto.ByteResource;
import com.fisa.dailytravel.post.dto.PostPagingRequest;
import com.fisa.dailytravel.post.dto.PostPagingResponse;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.dto.PostResponse;
import com.fisa.dailytravel.post.dto.PostSearchPagingRequest;
import com.fisa.dailytravel.post.fasade.RedissonLockPostFacade;
import com.fisa.dailytravel.post.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostServiceImpl postService;
    private final RedissonLockPostFacade redissonLockPostFacade;
    private final S3Uploader s3Uploader;

    @PostMapping("/v1/post")
    public ApiResponse<String> createPost(@ModelAttribute PostRequest postRequest, JwtAuthenticationToken principal) throws IOException {
        String uuid = principal.getName();
        return ApiResponse.ok(redissonLockPostFacade.savePostUseRedisson(uuid, postRequest));
    }

    @GetMapping("/v1/post/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable("id") String postId, CommentPageRequest commentPageRequest, JwtAuthenticationToken principal) {
        String uuid = principal.getName();
        return ApiResponse.ok(postService.getPost(uuid, Long.valueOf(postId), commentPageRequest));
    }

    @GetMapping("/v1/post/image")
    public ResponseEntity<byte[]> getImage(@RequestParam("s3url") String s3url, JwtAuthenticationToken principal) throws IOException {
        String uuid = principal.getName();
        ByteResource byteResource = s3Uploader.downloadImage(s3url);


        String fileName = s3url.substring(s3url.lastIndexOf("/") + 1);
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(byteResource.getContentType()))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(byteResource.getResource());
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
