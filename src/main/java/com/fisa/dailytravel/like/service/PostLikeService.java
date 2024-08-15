package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.dto.PostRequest;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface PostLikeService {

    public void insertPost(PostRequest pDTO, JwtAuthenticationToken principal);

}