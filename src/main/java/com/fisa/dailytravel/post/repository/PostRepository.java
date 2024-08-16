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
public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"postHashtags", "images"})
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);


    @EntityGraph(attributePaths = {"postHashtags.hashtag", "comments.user", "user", "comments"})
    Optional<Post> findById(Long id);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId ORDER BY p.createdAt DESC ")
    List<Post> findLatestPostByUserId(@Param("userId") Long userId, Pageable pageable);

    int countByTitle(String title);
}
