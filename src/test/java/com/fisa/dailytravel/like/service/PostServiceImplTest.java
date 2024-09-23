package com.fisa.dailytravel.like.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisa.dailytravel.like.dto.PostRequest;
import com.fisa.dailytravel.like.repository.PostLikeRepository;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
class PostServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    private ModelMapper mapper = new ModelMapper();

    @Test
    public void insertPost() throws JsonProcessingException {
        String uuid = "111969318487959339341";
        PostRequest pDTO = PostRequest.builder()
                .postTitle("Sample Title")
                .postContent("This is a sample post content.")
                .placeName("Sample Place")
                .likesCount(100)
                .thumbnail("sample_thumbnail.jpg")
                .latitude(37.7749)
                .longitude(-122.4194)
                .build();

        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));

        Post post = mapper.map(pDTO, Post.class);
        post.setUser(user);

        //when
        postLikeRepository.save(post);

        //then
        Optional<Post> findPost = postLikeRepository.findById(23L);

        Assertions.assertThat(findPost).isPresent();
        Assertions.assertThat("Sample Title").isEqualTo(findPost.get().getTitle());
    }

}