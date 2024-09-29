package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{

//    List<Post> findAllByIdIn(List<Long> postIds);
//
//    //    @EntityGraph(attributePaths = {"postHashtags.hashtag", "images"})
//    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
//
//    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
//    List<Post> findAllByOrderByCreatedAtDesc(int page, int count);
//
//    @EntityGraph(attributePaths = {"postHashtags.hashtag", "comments.user", "user", "comments"})
//    Optional<Post> findById(Long id);
//
//    @EntityGraph(attributePaths = {"postHashtags.hashtag", "user"})
//    Optional<Post> findPostAndPostHashtagsById(Long id);
//
//    @Query("SELECT p FROM Post p WHERE p.user.id = :userId ORDER BY p.createdAt DESC ")
//    List<Post> findLatestPostByUserId(@Param("userId") Long userId, Pageable pageable);
//
//    List<Post> findByContentContaining(String content, Pageable pageable);
//
//    int countByTitle(String title);
}
