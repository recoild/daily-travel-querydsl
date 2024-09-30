package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.config.CustomDataJpaTest;
import com.fisa.dailytravel.like.models.Like;
import com.fisa.dailytravel.like.repository.LikeRepository;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.models.Hashtag;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.models.PostHashtag;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@CustomDataJpaTest
public class PostRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PostHashtagRepository postHashtagRepository;

    @Autowired
    private HashTagRepository hashtagRepository;

    @Test
    public void 좋아요_누른_게시글_조회_테스트() {
        //given

        //유저 생성
        User user = new User();
        user.setUuid("1");
        user.setEmail("test@test.com");
        user.setNickname("test");
        user.setIsDeleted(false);

        //게시글 생성
        List<Post> posts = List.of(
                Post.builder()
                        .title("test")
                        .content("test")
                        .userId(1L)
                        .build(),
                Post.builder()
                        .title("test2")
                        .content("test2")
                        .userId(1L)
                        .build(),
                Post.builder()
                        .title("test3")
                        .content("test3")
                        .userId(1L)
                        .build()
        );

        //좋아요 생성
        List<Like> likes = List.of(
                Like.builder()
                        .postId(1L)
                        .userId(1L)
                        .build(),
                Like.builder()
                        .postId(2L)
                        .userId(1L)
                        .build()
        );

        //해쉬태그 생성
        List<Hashtag> hashtags = List.of(
                Hashtag.builder()
                        .hashtagName("test")
                        .build(),
                Hashtag.builder()
                        .hashtagName("test2")
                        .build()
        );

        //해쉬태그 교차 테이블 생성
        List<PostHashtag> postHashtags = List.of(
                PostHashtag.builder()
                        .postId(1L)
                        .hashtagId(1L)
                        .build(),
                PostHashtag.builder()
                        .postId(1L)
                        .hashtagId(2L)
                        .build()
        );

        //when
        User savedUser = userRepository.save(user);
        postRepository.saveAll(posts);
        likeRepository.saveAll(likes);
        hashtagRepository.saveAll(hashtags);
        postHashtagRepository.saveAll(postHashtags);

        //then
        Page<PostPreviewResponse> likedPosts = likeRepository.findFavoritePostsByUserId(1L, PageRequest.of(0, 10));
        assertThat(likedPosts.getContent().size()).isEqualTo(2);
    }
}
