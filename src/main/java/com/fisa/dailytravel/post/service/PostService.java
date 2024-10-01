package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.post.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface PostService {
    String savePost(String uuid, PostRequest postRequest) throws Exception;

    PostResponse getPost(String uuid, Long postId);

    Page<PostPreviewResponse> getPosts(String uuid, Pageable pageRequest) throws Exception;

    String modifyPost(String uuid, PostRequest postRequest) throws Exception;

    String deletePost(String uuid, Long postId);

    PostPagingResponse searchPosts(String uuid, PostSearchPagingRequest postSearchPagingRequest) throws Exception;
}
