package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.dto.LikeResponse;

public interface LikeService {

    public Boolean likeToggle(Long postId, String uuid);

    public LikeResponse favoritePosts(String uuid, int page, int count);
}
