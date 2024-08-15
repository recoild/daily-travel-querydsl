package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.dto.LikeResponse;
import com.fisa.dailytravel.like.models.Like;
import com.fisa.dailytravel.like.repository.LikeRepository;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.models.Image;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public Boolean likeToggle(Long postId, String uuid){
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

    @Transactional(readOnly = true)
    @Override
    public LikeResponse favoritePosts(String uuid, int page, int count) {
        User user = userRepository.findByUuid(uuid);

        PageRequest posts = PageRequest.of(page, count);
        Page<Post> postList = likeRepository.findFavoritePostsByUserId(user.getId(), posts);

        List<PostPreviewResponse> likePreview = postList.stream()
                .map(post -> PostPreviewResponse.of(
                        post,
                        post.getImages().stream().map(Image::getImagePath).collect(Collectors.toList()),
                        post.getPostHashtags().stream().map(hashtag -> hashtag.getHashtag().getHashtagName()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return LikeResponse.builder()
                .page(page)
                .postPreviewResponses(likePreview)
                .isEnd(postList.isLast())
                .build();
    }
}
