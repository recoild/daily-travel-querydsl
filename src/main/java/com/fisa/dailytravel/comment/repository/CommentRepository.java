package com.fisa.dailytravel.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fisa.dailytravel.comment.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.createdAt ASC, c.id ASC")
    Page<Comment> findByPostIdOrderByCreatedAtAscIdAsc(@Param("postId") Long postId, Pageable pageable);

    Page<Comment> findAll(Pageable pageable);
}
