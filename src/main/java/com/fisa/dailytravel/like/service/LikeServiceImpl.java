package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.models.Like;
import com.fisa.dailytravel.like.repository.LikeRepository;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class
LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Transactional
    @Override
    public Boolean likeToggle(Long postId, String uuid) {
        User user = userRepository.findByUuid(uuid);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 유저");
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물"));
        Long userId = user.getId();

        return likeRepository.findByPostIdAndUserId(postId, userId)
                .map(like -> {
                    likeRepository.deleteByPostIdAndUserId(postId, userId);
                    post.setLikesCount(post.getLikesCount() - 1);
                    postRepository.save(post);
                    return false;
                }).orElseGet(() -> {
                    Like newLike = Like.builder()
                            .post(post)
                            .user(user)
                            .build();
                    likeRepository.save(newLike);
                    post.setLikesCount(post.getLikesCount() + 1);
                    postRepository.save(post);
                    return true;
                });
    }
}
