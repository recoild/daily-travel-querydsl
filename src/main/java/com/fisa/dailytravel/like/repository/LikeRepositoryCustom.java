package com.fisa.dailytravel.like.repository;

import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LikeRepositoryCustom {
    Page<List<PostPreviewResponse>> findFavoritePostsByUserId(Long userId, Pageable pageable);
}
