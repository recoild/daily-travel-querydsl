package com.fisa.dailytravel.like.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisa.dailytravel.global.util.JsonUtil;
import com.fisa.dailytravel.like.dto.PostDTO;
import com.fisa.dailytravel.like.dto.PrincipalDTO;
import com.fisa.dailytravel.like.service.PostService;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.controller.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Controller
public class PostController {

    private final PostService postService;

    private ModelMapper mapper = new ModelMapper();
    private ObjectMapper obMapper = JsonUtil.getInstance();

    public ResponseEntity<?> insertPost(@RequestBody PostDTO pDTO, JwtAuthenticationToken principal) throws JsonProcessingException {
        log.info("insert info start");
        Post post = mapper.map(pDTO, Post.class);

        Map<String, Object> tokenAttributes = principal.getTokenAttributes();
        String json = obMapper.writeValueAsString(tokenAttributes);
        PrincipalDTO principalDTO = obMapper.readValue(json, PrincipalDTO.class);

        // 이메일, 이름, 닉네임 not null
        User user = new User();
        user.setEmail(principalDTO.getEmail());
        user.setNickname(principalDTO.getName());
        user.setUuid(principalDTO.getSub());

        post.setUser(user);

        postService.insertPost(post);


        log.info("insert info end");
        return ResponseEntity.ok("성공");
    }


}
