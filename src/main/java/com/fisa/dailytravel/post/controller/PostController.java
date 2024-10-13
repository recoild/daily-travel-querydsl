package com.fisa.dailytravel.post.controller;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.global.dto.ByteResource;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    private final S3Uploader s3Uploader;

    @PostMapping("/v1/post")
    public ResponseEntity<Void> savePost(@ModelAttribute PostRequest postRequest, JwtAuthenticationToken principal) throws Exception {
        String uuid = principal.getName();
        postService.savePost(uuid, postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/v1/post")
    public ResponseEntity<Page<PostPreviewResponse>> getPosts(Pageable pageRequest, JwtAuthenticationToken principal) throws Exception {
        String uuid = principal.getName();
        Page<PostPreviewResponse> posts = postService.getPosts(uuid, pageRequest);
        return ResponseEntity.ok(posts);
    }
//
//    @GetMapping("/v1/post/{id}")
//    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id, JwtAuthenticationToken principal) throws Exception{
//        PostResponse post = postService.getPost(principal.getName(),id);
//        return ResponseEntity.ok(post);
//    }


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

}