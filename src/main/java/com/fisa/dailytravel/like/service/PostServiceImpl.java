package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.repository.PostLikeRepository;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.controller.models.User;
import com.fisa.dailytravel.user.controller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    // 게시판 생성
    @Override
    public void insertPost(Post post) {
        postLikeRepository.save(post);
    }
}
