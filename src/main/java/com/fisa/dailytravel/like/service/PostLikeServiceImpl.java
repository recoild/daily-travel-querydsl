package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.repository.PostLikeRepository;
import com.fisa.dailytravel.post.models.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    // 게시판 생성
    @Override
    public void insertPost(Post post) {
        postLikeRepository.save(post);
    }
}
