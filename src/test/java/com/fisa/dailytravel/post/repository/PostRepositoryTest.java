package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.comment.repository.CommentRepository;
import com.fisa.dailytravel.config.CustomDataJpaTest;
import com.fisa.dailytravel.like.models.Like;
import com.fisa.dailytravel.like.repository.LikeRepository;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.dto.PostResponse;
import com.fisa.dailytravel.post.models.Hashtag;
import com.fisa.dailytravel.post.models.Image;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.models.PostHashtag;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        //given
        User user = User.builder()
                .uuid("1")
                .email("test@test.com")
                .nickname("test")
                .isDeleted(false)
                .build();

        //게시글 생성
        List<Post> posts = List.of(
                Post.builder()
                        .title("test")
                        .content("test")
                        .userId(1L)
                        .placeName("test")
                        .likesCount(0)
                        .build(),
                Post.builder()
                        .title("test2")
                        .content("test2")
                        .userId(1L)
                        .placeName("test")
                        .likesCount(0)
                        .build(),
                Post.builder()
                        .title("test3")
                        .content("test3")
                        .userId(1L)
                        .placeName("test")
                        .likesCount(0)
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

        //이미지 생성
        List<Image> images = List.of(
                Image.builder()
                        .imagePath("test")
                        .postId(1L)
                        .build(),
                Image.builder()
                        .imagePath("test2")
                        .postId(1L)
                        .build()
        );

        //댓글 생성
        List<Comment> comments = List.of(
                Comment.builder()
                        .content("test")
                        .postId(1L)
                        .userId(1L)
                        .build(),
                Comment.builder()
                        .content("test2")
                        .postId(1L)
                        .userId(1L)
                        .build()
        );

        //when
        User savedUser = userRepository.save(user);
        postRepository.saveAll(posts);
        likeRepository.saveAll(likes);
        hashtagRepository.saveAll(hashtags);
        postHashtagRepository.saveAll(postHashtags);
        imageRepository.saveAll(images);
        commentRepository.saveAll(comments);
    }

    @Test
    public void 좋아요_누른_게시글_조회_테스트() {
        //then
        Page<PostPreviewResponse> likedPosts = likeRepository.findFavoritePostsByUserId(1L, PageRequest.of(0, 10));
        assertThat(likedPosts.getContent().size()).isEqualTo(2);
    }

    @Test
    public void 게시글_조회_테스트() {
        PostResponse postResponse = postRepository.getPost(1L, 1L);

        assertThat(postResponse.getTitle()).isEqualTo("test");

    }

    @Test
    public void 게시글_목록_테스트() {
        Page<PostPreviewResponse> postPreviewResponses = postRepository.getPosts(1L, PageRequest.of(0, 10));
        assertThat(postPreviewResponses.getContent().size()).isEqualTo(3);
    }
}
