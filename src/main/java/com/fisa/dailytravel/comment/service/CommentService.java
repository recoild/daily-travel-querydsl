package com.fisa.dailytravel.comment.service;

import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(String uuid, CommentRequest commentRequest);
    List<CommentResponse> getAllComments(Long id);
    void deleteComment(Long id, Long commentsId);
}
