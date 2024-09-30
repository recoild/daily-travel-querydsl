//package com.fisa.dailytravel.like.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fisa.dailytravel.global.dto.ApiResponse;
//import com.fisa.dailytravel.like.dto.PostRequest;
//import com.fisa.dailytravel.like.service.PostLikeService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RequiredArgsConstructor
//@Slf4j
//@RestController
//public class PostLikeController {
//
//    private final PostLikeService postLikeService;
//
//    @PostMapping("/v1/mockPost")
//    public ApiResponse insertPost(@RequestBody PostRequest pDTO, JwtAuthenticationToken principal) throws JsonProcessingException {
//        postLikeService.insertPost(pDTO, principal);
//        return ApiResponse.ok();
//    }
//
//}
