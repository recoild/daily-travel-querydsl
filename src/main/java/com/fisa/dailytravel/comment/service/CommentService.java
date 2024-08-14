package com.fisa.dailytravel.comment.service;

import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface CommentService {
    String createComment(String uuid, CommentRequest commentRequest);
    Page<Comment> getPageComments(Long postId, CommentPageRequest commentPageRequest);
    String deleteComment(String uuid, CommentRequest commentRequest);
}
