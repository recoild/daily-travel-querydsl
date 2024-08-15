package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.Image;
import com.fisa.dailytravel.post.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPostId(Long postId);

    void deleteAllByPost(Post post);
}
