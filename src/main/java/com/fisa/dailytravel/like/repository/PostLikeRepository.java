package com.fisa.dailytravel.like.repository;

import com.fisa.dailytravel.post.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface PostLikeRepository extends JpaRepository<Post, Long> {

}
