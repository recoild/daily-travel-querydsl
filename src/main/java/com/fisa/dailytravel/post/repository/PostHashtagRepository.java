package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long>, PostHashtagRespositoryCustom {
    List<PostHashtag> findAllByPostId(Long postId);
}
