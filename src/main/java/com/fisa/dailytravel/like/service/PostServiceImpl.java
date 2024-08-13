package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.repository.PostRepository;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.controller.models.User;
import com.fisa.dailytravel.user.controller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시판 생성
    @Override
    public void insertPost(Post post) {
        postRepository.save(post);
    }
}
