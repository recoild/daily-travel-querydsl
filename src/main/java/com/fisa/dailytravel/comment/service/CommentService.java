package com.fisa.dailytravel.comment.service;

import java.util.List;

import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;

public interface CommentService {

    String createComment(String uuid, CommentRequest commentRequest);
    List<CommentResponse> getPageComments(Long postId, CommentPageRequest commentPageRequest);
    String deleteComment(String uuid, CommentRequest commentRequest);

}
