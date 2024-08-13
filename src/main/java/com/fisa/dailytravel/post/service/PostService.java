package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.post.dto.PostRequest;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    String savePost(String userId, PostRequest postRequest);
    
}
