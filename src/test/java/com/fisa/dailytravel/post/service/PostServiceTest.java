package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.config.CustomSpringBootTest;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import com.fisa.dailytravel.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        File imageFile = new File("src/test/resources/images/pikachu.png");
        FileInputStream inputStream = new FileInputStream(imageFile);
        MultipartFile multipartFile = new MockMultipartFile(
                "imageFiles",
                imageFile.getName(),
                "image/png",
                inputStream
        );


        userRepository.save(user);
        userRepository.flush();

        PostRequest postRequest = new PostRequest();
        postRequest.setContent("content");
        postRequest.setTitle("title");
        postRequest.setHashtags(List.of("hashtag1", "hashtag2"));
        postRequest.setPlaceName("placeName");
        postRequest.setImageFiles(List.of(multipartFile));


        Post savedPost = postService.savePost("uuid1", postRequest);


        // then
        assertNotNull(savedPost);
        assertNotNull(savedPost.getId());
    }
}
