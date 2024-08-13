package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.dto.PostResponse;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    String savePost(String uuid, PostRequest postRequest);

    PostResponse getPost(String uuid, Long postId);
}
