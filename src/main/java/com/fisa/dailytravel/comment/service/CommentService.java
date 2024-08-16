package com.fisa.dailytravel.comment.service;

import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    String createComment(String uuid, CommentRequest commentRequest);

    List<CommentResponse> getPageComments(Long postId, CommentPageRequest commentPageRequest);

    String deleteComment(String uuid, CommentRequest commentRequest);
}
