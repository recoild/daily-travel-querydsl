package com.fisa.dailytravel.like.repository;

import com.fisa.dailytravel.like.models.Like;
import com.fisa.dailytravel.post.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostIdAndUserId(Long postId, Long userId);

    @Query(value = "SELECT p FROM Like l JOIN l.post p WHERE l.user.id = :userId",
            countQuery = "SELECT COUNT(l) FROM Like l WHERE l.user.id = :userId")
    Page<Post> findFavoritePostsByUserId(Long userId, Pageable pageable);

}
