package com.fisa.dailytravel.post;

import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.fasade.RedissonLockPostFacade;
import com.fisa.dailytravel.post.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private RedissonLockPostFacade redissonLockPostFacade;

    @Autowired
    private PostRepository postRepository;

    private PostRequest createRequestPost() {
        MockMultipartFile imageFile2 = new MockMultipartFile("imageFile2", "image2.jpg", "image/jpeg", "image content 2".getBytes());
        MockMultipartFile imageFile1 = new MockMultipartFile("imageFile1", "image1.jpg", "image/jpeg", "image content 1".getBytes());
        List<MultipartFile> imageFiles = Arrays.asList(imageFile1, imageFile2);
        List<String> hashtags = Arrays.asList("travel", "vacation");

        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Title");
        postRequest.setContent("Content");
        postRequest.setImageFiles(imageFiles);
        postRequest.setPlaceName("Place Name");
        postRequest.setHashtags(hashtags);
        postRequest.setLatitude(37.7749);
        postRequest.setLongitude(-122.4567);

        return postRequest;
    }

    @Test
    public void testConcurrentPostSave() throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        PostRequest postRequest = createRequestPost();

        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    try {
                        redissonLockPostFacade.savePostUseRedisson(uuid, postRequest);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();

        int postCount = postRepository.countByTitle(postRequest.getTitle());
        Assertions.assertEquals(threadCount, postCount);
    }

    @Test
    public void testJPAPagingQuery() {
        int page = 0;
        int size = 3;

        // @Query 사용
        long startTimeQuery = System.currentTimeMillis();
        postRepository.findAllByOrderByCreatedAtDesc(page, size);
        long endTimeQuery = System.currentTimeMillis();
        long durationQuery = endTimeQuery - startTimeQuery;

        // Pageable 사용
        Pageable pageable = PageRequest.of(page, size);
        long startTimePageable = System.currentTimeMillis();
        postRepository.findAllByOrderByCreatedAtDesc(pageable);
        long endTimePageable = System.currentTimeMillis();
        long durationPageable = endTimePageable - startTimePageable;

        System.out.println("@Query 조회시간 : " + durationQuery + " ms");
        System.out.println("Pageable 조회시간 : " + durationPageable + " ms");

        assertTrue(durationQuery >= 0);
        assertTrue(durationPageable >= 0);
    }
}
