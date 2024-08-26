package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.post.dto.*;

import java.io.IOException;

public interface PostService {
    String savePost(String uuid, PostRequest postRequest) throws IOException;

    PostResponse getPost(String uuid, Long postId);

    PostPagingResponse getAllPosts(String uuid, PostPagingRequest postPagingRequest);

    String modifyPost(String uuid, PostRequest postRequest) throws IOException;

    String deletePost(String uuid, Long postId);

    PostPagingResponse searchPosts(String uuid, PostSearchPagingRequest postSearchPagingRequest) throws Exception;

    PostPagingResponse searchPostsWithES(String uuid, PostSearchPagingRequest search) throws Exception;
}
