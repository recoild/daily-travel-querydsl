package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.dto.PostRequest;
import com.fisa.dailytravel.like.repository.PostLikeRepository;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void insertPost(PostRequest pDTO, JwtAuthenticationToken principal) {
        Post post = modelMapper.map(pDTO, Post.class);
        String uuid = principal.getName();

        User user = userRepository.findByUuid(uuid);
        post.setUser(user);
       // post.setCreatedAt(LocalDate.now());
        postLikeRepository.save(post);
    }
}