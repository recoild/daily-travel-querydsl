package com.fisa.dailytravel.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;

@Service
public interface CommentService {
    String saveComment(String uuid, CommentRequest commentRequest);

    List<CommentResponse> getComments(Long postId, CommentPageRequest commentPageRequest);

    String deleteComment(String uuid, CommentRequest commentRequest);
}
