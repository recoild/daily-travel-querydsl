package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.dto.PostResponse;
import com.fisa.dailytravel.post.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post savePost(String uuid, PostRequest postRequest) throws Exception;

    PostResponse getPost(Long userId, Long postId) throws Exception;

    Page<PostPreviewResponse> getPosts(Long userId, Pageable pageRequest) throws Exception;

//    String modifyPost(String uuid, PostRequest postRequest) throws Exception;
//
//    String deletePost(String uuid, Long postId);
//
//    PostPagingResponse searchPosts(String uuid, PostSearchPagingRequest postSearchPagingRequest) throws Exception;
}
