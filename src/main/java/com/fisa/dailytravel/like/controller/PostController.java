package com.fisa.dailytravel.like.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.like.dto.PostDTO;
import com.fisa.dailytravel.like.dto.PrincipalDTO;
import com.fisa.dailytravel.like.repository.PostRepository;
import com.fisa.dailytravel.like.service.PostService;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.controller.models.User;
import com.fisa.dailytravel.user.controller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    @PostMapping("/v1/post")
    public ApiResponse insertPost(@RequestBody PostDTO pDTO, JwtAuthenticationToken principal) throws JsonProcessingException {
        log.info("insert info start");
        Post post = mapper.map(pDTO, Post.class);

        String uuid = principal.getName();
        User findUser = userRepository.findByUuid(uuid);
        post.setUser(findUser);
        post.setCreatedAt(LocalDate.now());
        postService.insertPost(post);

        log.info("insert info end");
        return ApiResponse.ok();
    }


}
