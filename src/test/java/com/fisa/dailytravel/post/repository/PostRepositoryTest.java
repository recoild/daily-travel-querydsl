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

    private User savedUser;
    private List<Post> savedPosts;
    private List<Hashtag> savedHashtags;

    @BeforeEach
    public void setUp() {
        //delete all
        userRepository.deleteAll();
        postRepository.deleteAll();
        likeRepository.deleteAll();
        imageRepository.deleteAll();
        postHashtagRepository.deleteAll();
        hashtagRepository.deleteAll();
        commentRepository.deleteAll();

        // 사용자 생성 및 저장
        User user = User.builder()
                .uuid("1")
                .email("test@test.com")
                .nickname("test")
                .isDeleted(false)
                .build();
        savedUser = userRepository.save(user);

        // 게시글 생성 (실제 저장된 사용자 ID 사용)
        List<Post> posts = List.of(
                Post.builder()
                        .title("test")
                        .content("test")
                        .userId(savedUser.getId()) // 실제 저장된 사용자 ID 사용
                        .placeName("test")
                        .likesCount(0)
                        .build(),
                Post.builder()
                        .title("test2")
                        .content("test2")
                        .userId(savedUser.getId())
                        .placeName("test")
                        .likesCount(0)
                        .build(),
                Post.builder()
                        .title("test3")
                        .content("test3")
                        .userId(savedUser.getId())
                        .placeName("test")
                        .likesCount(0)
                        .build()
        );
        savedPosts = postRepository.saveAll(posts);

        // 좋아요 생성 (실제 저장된 게시글 ID와 사용자 ID 사용)
        List<Like> likes = List.of(
                Like.builder()
                        .postId(savedPosts.get(0).getId())
                        .userId(savedUser.getId())
                        .build(),
                Like.builder()
                        .postId(savedPosts.get(1).getId())
                        .userId(savedUser.getId())
                        .build()
        );
        likeRepository.saveAll(likes);

        // 해시태그 생성 및 저장
        List<Hashtag> hashtags = List.of(
                Hashtag.builder()
                        .hashtagName("test")
                        .build(),
                Hashtag.builder()
                        .hashtagName("test2")
                        .build()
        );
        savedHashtags = hashtagRepository.saveAll(hashtags);

        // 해시태그 교차 테이블 생성 및 저장
        List<PostHashtag> postHashtags = List.of(
                PostHashtag.builder()
                        .postId(savedPosts.get(0).getId())
                        .hashtagId(savedHashtags.get(0).getId())
                        .build(),
                PostHashtag.builder()
                        .postId(savedPosts.get(0).getId())
                        .hashtagId(savedHashtags.get(1).getId())
                        .build()
        );
        postHashtagRepository.saveAll(postHashtags);

        // 이미지 생성 및 저장
        List<Image> images = List.of(
                Image.builder()
                        .imagePath("test")
                        .postId(savedPosts.get(0).getId())
                        .build(),
                Image.builder()
                        .imagePath("test2")
                        .postId(savedPosts.get(0).getId())
                        .build()
        );
        imageRepository.saveAll(images);

        // 댓글 생성 및 저장
        List<Comment> comments = List.of(
                Comment.builder()
                        .content("test")
                        .postId(savedPosts.get(0).getId())
                        .userId(savedUser.getId())
                        .build(),
                Comment.builder()
                        .content("test2")
                        .postId(savedPosts.get(0).getId())
                        .userId(savedUser.getId())
                        .build()
        );
        commentRepository.saveAll(comments);
    }

    @Test
    public void 좋아요_누른_게시글_조회_테스트() {
        //when
        Page<PostPreviewResponse> likedPosts = likeRepository.findFavoritePostsByUserId(savedUser.getId(), PageRequest.of(0, 10));

        //then
        assertThat(likedPosts.getContent().size()).isEqualTo(2);
    }

    @Test
    public void 게시글_조회_테스트() {
        //when
        PostResponse postResponse = postRepository.getPost(savedUser.getUuid(), savedPosts.get(0).getId());

        //then
        assertThat(postResponse.getTitle()).isEqualTo("test");
    }

    @Test
    public void 게시글_목록_테스트() {
        //when
        Page<PostPreviewResponse> posts = postRepository.getPosts(savedUser.getUuid(), PageRequest.of(0, 10));

        //then
        assertThat(posts.getContent().size()).isEqualTo(3);
    }
}
