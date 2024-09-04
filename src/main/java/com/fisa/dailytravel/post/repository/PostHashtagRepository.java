package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.models.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {
    List<PostHashtag> findByPostId(Long postId);

    void deleteByPost(Post post);

    @Modifying
    @Query("DELETE FROM PostHashtag ph WHERE ph.post.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);
}
