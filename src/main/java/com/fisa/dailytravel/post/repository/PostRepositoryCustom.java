package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    PostResponse getPost(Long userId, Long postId);

    Page<PostPreviewResponse> getPosts(Long userId, Pageable pageRequest);
}
