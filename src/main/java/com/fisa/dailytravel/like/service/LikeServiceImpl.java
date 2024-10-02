package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.dto.LikeResponse;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl  implements LikeService{
    @Override
    public Boolean likeToggle(Long postId, String uuid) {
        return null;
    }

    @Override
    public LikeResponse favoritePosts(String uuid, int page, int count) {
        return null;
    }
}
