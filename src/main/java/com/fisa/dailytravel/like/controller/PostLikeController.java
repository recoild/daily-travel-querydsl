package com.fisa.dailytravel.like.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fisa.dailytravel.global.dto.ApiResponse;
import com.fisa.dailytravel.like.dto.PostDTO;
import com.fisa.dailytravel.like.service.PostLikeService;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.controller.models.User;
import com.fisa.dailytravel.user.controller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final UserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    @PostMapping("/v1/mockPost")
    public ApiResponse insertPost(@RequestBody PostDTO pDTO, JwtAuthenticationToken principal) throws JsonProcessingException {
        log.info("insert info start");
        Post post = mapper.map(pDTO, Post.class);

        String uuid = principal.getName();
        User findUser = userRepository.findByUuid(uuid);

        post.setUser(findUser);
        post.setCreatedAt(LocalDate.now());

        postLikeService.insertPost(post);

        log.info("insert info end");
        return ApiResponse.ok();
    }


}
