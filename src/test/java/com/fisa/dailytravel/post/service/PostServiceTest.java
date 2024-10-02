package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.config.CustomSpringBootTest;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import com.fisa.dailytravel.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@CustomSpringBootTest
public class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public  void testSavePost() throws Exception{
        User user = new User();
        user.setUuid("uuid1");
        user.setEmail("test@test.com");
        user.setNickname("test");
        user.setIsDeleted(false);

        userRepository.save(user);
        userRepository.flush();

        PostRequest postRequest = new PostRequest();
        postRequest.setContent("content");
        postRequest.setTitle("title");
        postRequest.setHashtags(List.of("hashtag1", "hashtag2"));
        postRequest.setPlaceName("placeName");

        postService.savePost("uuid1", postRequest);

    }
}
