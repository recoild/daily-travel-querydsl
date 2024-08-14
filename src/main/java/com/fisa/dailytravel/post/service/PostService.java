package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.post.dto.PostPagingRequest;
import com.fisa.dailytravel.post.dto.PostPagingResponse;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.dto.PostResponse;

import java.io.IOException;

public interface PostService {

    String savePost(String uuid, PostRequest postRequest) throws IOException;

    PostResponse getPost(String uuid, Long postId);

    PostPagingResponse getAllPosts(String uuid, PostPagingRequest postPagingRequest);
}
