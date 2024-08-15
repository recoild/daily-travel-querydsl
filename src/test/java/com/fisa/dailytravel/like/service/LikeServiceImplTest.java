package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.dto.LikeResponse;
import com.fisa.dailytravel.like.fasade.RedissonLockLikeFacade;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeServiceImplTest {

    @Autowired
    LikeService likeService;

    @Autowired
    RedissonLockLikeFacade redissonLockLikeFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Long postId = 11L;
    private List<String> userUuids;


    @BeforeEach
    void setUp() {
        userUuids = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String uuid = UUID.randomUUID().toString();
            User user = User.builder()
                    .uuid(uuid)
                    .email(uuid + "@example.com")
                    .nickname("user" + i)
                    .profileImagePath(null)
                    .isDeleted(false)
                    .build();
            userRepository.save(user);
            userUuids.add(uuid);
        }
    }

    @Test void 레디슨_락_서비스_적용_테스트() throws InterruptedException {
        //given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<Boolean> results = new ArrayList<>(Collections.nCopies(threadCount, false));

        for (int i = 0; i < threadCount; i++) {
            final String uuid = userUuids.get(i);  // 순차적으로 각 유저의 UUID 사용
            final int index = i;
            executorService.submit(() -> {
                try {
                    boolean result = redissonLockLikeFacade.serviceToggle(postId, uuid);
                    results.add(index, result);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //when
        Optional<Post> post = postRepository.findById(postId);
        int likeCount = post.get().getLikesCount();

        //then
        assertEquals(100, likeCount);
        assertTrue(results.get(threadCount - 1));
    }

    @Test
    public void 동시에_100명의_유저가_좋아요를_누름_레디슨_락_적용() throws InterruptedException {
        //given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final String uuid = userUuids.get(i);  // 순차적으로 각 유저의 UUID 사용
            executorService.submit(() -> {
                try {
                    redissonLockLikeFacade.likeToggle(postId, uuid);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //when
        Optional<Post> post = postRepository.findById(postId);
        int likeCount = post.get().getLikesCount();

        //then
        assertEquals(100, likeCount);
    }

    @Test
    public void _100명의_유저가_좋아요를_누름_동시성이슈_발생() throws InterruptedException {
        //given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final String uuid = userUuids.get(i);  // 순차적으로 각 유저의 UUID 사용
            executorService.submit(() -> {
                try {
                    likeService.likeToggle(postId, uuid);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        //when
        Optional<Post> post = postRepository.findById(postId);

        //then
        int likeCount = post.get().getLikesCount();
        assertNotEquals(100, likeCount);
    }

    @Test
    void 즐겨찾기_좋아요_누른_게시물_가져오기(){
        //given
        String uuid = "111969318487959339341";
        int page = 0;
        int count = 10;

        //when
        LikeResponse postResponses = likeService.favoritePosts(uuid, page, count);

        //then
        Assertions.assertThat(postResponses.getPostPreviewResponses()).isNotEmpty();
        Assertions.assertThat(postResponses.getPostPreviewResponses().size()).isEqualTo(10);
    }

    @Test
    void 좋아요를_안_눌렀으면_좋아요_등록(){
        //given
        String uuid = "111969318487959339341";
        Long postId = 11L;

        //when
        Boolean like = likeService.likeToggle(postId, uuid);

        //then
        Assertions.assertThat(like).isTrue();
    }

    @Test
    void 이미_좋아요_눌렀으면_좋아요_삭제(){
        //given
        String uuid = "111969318487959339341";
        Long postId = 11L;

        //when
        Boolean like = likeService.likeToggle(postId, uuid);

        //then
        Assertions.assertThat(like).isFalse();
    }

}