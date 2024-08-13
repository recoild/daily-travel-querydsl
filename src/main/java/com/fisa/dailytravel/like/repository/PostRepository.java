package com.fisa.dailytravel.like.repository;

import com.fisa.dailytravel.post.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
