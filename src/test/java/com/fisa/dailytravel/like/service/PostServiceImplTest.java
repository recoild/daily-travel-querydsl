package com.fisa.dailytravel.like.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisa.dailytravel.like.dto.PostDTO;
import com.fisa.dailytravel.like.dto.PrincipalDTO;
import com.fisa.dailytravel.like.repository.PostLikeRepository;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.controller.models.User;
import com.fisa.dailytravel.user.controller.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@SpringBootTest
class PostServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeRepository postRepository;

    private ModelMapper mapper = new ModelMapper();

    @Test
    @Transactional
    public void insertPost() throws JsonProcessingException {
        //given
        String data = "{"
                + "\"at_hash\": \"uoiS7Ahf1Jqu9Me1JqHT3A\","
                + "\"sub\": \"111969318487959339341\","
                + "\"email_verified\": true,"
                + "\"iss\": \"https://accounts.google.com\","
                + "\"given_name\": \"영하\","
                + "\"picture\": \"https://lh3.googleusercontent.com/a/ACg8ocITbuhJ_pg3L6has17MvaLkNzNK3L7CaVKAWsJKjyTGakAzWA=s96-c\","
                + "\"aud\": \"756324778211-bt04f318ugn8fb23jrdg2u495i5m18d8.apps.googleusercontent.com\","
                + "\"azp\": \"756324778211-bt04f318ugn8fb23jrdg2u495i5m18d8.apps.googleusercontent.com\","
                + "\"name\": \"최영하\","
                + "\"exp\": \"2024-08-13T06:41:23Z\","
                + "\"family_name\": \"최\","
                + "\"iat\": \"2024-08-13T05:41:23Z\","
                + "\"email\": \"gymlet789@gmail.com\""
                + "}";

        PostDTO pDTO = new PostDTO();
        pDTO.setPostTitle("제목");
        pDTO.setLatitude(37.123);
        pDTO.setLongitude(126.889);
        pDTO.setThumbnail("https://lh3.googleusercontent.com/a/ACg8ocITbuhJ_pg3L6has17MvaLkNzNK3L7CaVKAWsJKjyTGakAzWA=s96-c");
        pDTO.setLikesCount(2);
        pDTO.setPostContent("내용ㅇ입니다.");
        pDTO.setPlaceName("우리FIS");

        Post post = mapper.map(pDTO, Post.class);

        Map<String, Object> tokenAttributes = new HashMap<String, Object>();
        tokenAttributes.put("data", data);
        String json = (String) tokenAttributes.get("data");
        PrincipalDTO principalDTO = objectMapper.readValue(json, PrincipalDTO.class);

        String uuid = principalDTO.getSub();
        User findUser = userRepository.findByUuid(uuid);

        post.setUser(findUser);
        post.setCreatedAt(LocalDate.now());

        //when
        postRepository.save(post);

        //then
        Optional<Post> findPost = postRepository.findById(6L);

        Assertions.assertThat(findPost).isPresent();
        Assertions.assertThat("제목").isEqualTo(findPost.get().getTitle());
    }

}