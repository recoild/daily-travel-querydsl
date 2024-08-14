package com.fisa.dailytravel.like.repository;

import com.fisa.dailytravel.post.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<Post, Long> {

}
