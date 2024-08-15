package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.dto.LikeResponse;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.dto.PostResponse;
import com.fisa.dailytravel.post.models.Post;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface LikeService {

    public Boolean likeToggle(Long postId, String uuid);

    public LikeResponse favoritePosts(String uuid, int page, int count);


}
