package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.config.CustomDataJpaTest;
import com.fisa.dailytravel.like.models.Like;
import com.fisa.dailytravel.like.repository.LikeRepository;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@CustomDataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Test
    public void 사용자_저장_및_조회_테스트() {
        //given
        User user = new User();
        user.setUuid("1");
        user.setEmail("test@test.com");
        user.setNickname("test");
        user.setIsDeleted(false);

        //when
        User savedUser = userRepository.save(user);

        //then
        Optional<User> findUser = userRepository.findByUuid("1");
        assertTrue(findUser.isPresent());
        assertEquals("test", findUser.get().getNickname());
        assertEquals("test@test.com", findUser.get().getEmail());
        assertFalse(findUser.get().getIsDeleted());
        assertNotNull(savedUser.getId(), "Generated ID는 반드시 존재해야 합니다.");
    }
//
//    @Test
//    public void 프로필_최근_게시글_좋아요_조회_테스트() {
//        //given
//        User user = new User();
//        user.setUuid("1");
//        user.setEmail("test@test.com");
//        user.setNickname("test");
//        user.setIsDeleted(false);
//
//        List<Post> posts = List.of(
//                Post.builder().title("title1").content("content1").placeName("place1").thumbnail("thumbnail1")
//                        .user(user).build(),
//                Post.builder().title("title2").content("content2").placeName("place2").thumbnail("thumbnail2")
//                        .user(user).build(),
//                Post.builder().title("title3").content("content3").placeName("place3").thumbnail("thumbnail3")
//                        .user(user).build()
//        );
//
//        List<Like> likes = List.of(
//                Like.builder().post(posts.get(0)).user(user).build(),
//                Like.builder().post(posts.get(1)).user(user).build(),
//                Like.builder().post(posts.get(2)).user(user).build()
//        );
//
//        //when
//        userRepository.save(user);
//        postRepository.saveAll(posts);
//        likeRepository.saveAll(likes);
//
//        //then
//        Pageable pageable = PageRequest.of(0, 10);
//
//        Page<Post> findLikes = likeRepository.findFavoritePostsByUserId(user.getId(), pageable);
//        assertEquals(3, findLikes.getTotalElements());
//
//        Page<Post> findPosts = userRepository.findLatestPostsByUuid(user.getUuid(), pageable);
//        assertEquals(3, findPosts.getTotalElements());
//    }

}