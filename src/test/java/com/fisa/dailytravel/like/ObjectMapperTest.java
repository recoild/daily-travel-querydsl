package com.fisa.dailytravel.like;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisa.dailytravel.like.dto.PostRequest;
import com.fisa.dailytravel.like.dto.PrincipalDTO;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ObjectMapperTest {

    @Autowired
    ObjectMapper objectMapper;

    private ModelMapper mapper = new ModelMapper();


    /**
     * ObjectMapper 수동 DI 테스트
     *
     * @throws JsonProcessingException
     */
    @Test
    public void objectMapperTest() throws JsonProcessingException {
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

        Map<String, Object> tokenAttributes = new HashMap<String, Object>();
        tokenAttributes.put("data", data);

        String json = (String) tokenAttributes.get("data");
        System.out.println("json = " + json);
        PrincipalDTO principalDTO = objectMapper.readValue(json, PrincipalDTO.class);

        // 이메일, 이름, 닉네임 not null
        User user = new User();
        user.setEmail(principalDTO.getEmail());
        user.setNickname(principalDTO.getName());
        user.setUuid(principalDTO.getSub());

        Assertions.assertThat("최영하").isEqualTo(principalDTO.getName());
    }


    /**
     * ModelMap 테스트
     */
    @Test
    public void modelMapperTest() {
        PostRequest pDTO = new PostRequest();

        pDTO.setPostTitle("제목");
        pDTO.setLatitude(37.123);
        pDTO.setLongitude(126.889);
        pDTO.setThumbnail("https://lh3.googleusercontent.com/a/ACg8ocITbuhJ_pg3L6has17MvaLkNzNK3L7CaVKAWsJKjyTGakAzWA=s96-c");
        pDTO.setLikesCount(2);
        pDTO.setPostContent("내용입니다.");
        pDTO.setPlaceName("우리FIS");

        Post post = mapper.map(pDTO, Post.class);

        Assertions.assertThat("제목").isEqualTo(post.getTitle());

    }


}
