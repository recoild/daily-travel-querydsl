package com.fisa.dailytravel.comment.repository;

import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtAscIdAsc(Long postId);
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    Page<Comment> findAll(Pageable pageable);
//    Page<CommentResponse> findAll (Pageable pageable);
}
