package com.fisa.dailytravel.like.repository;

import com.fisa.dailytravel.post.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeRepositoryCustom {
    Page<Post> findFavoritePostsByUserId(Long userId, Pageable pageable);
}
