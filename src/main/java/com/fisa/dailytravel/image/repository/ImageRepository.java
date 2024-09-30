package com.fisa.dailytravel.image.repository;

import com.fisa.dailytravel.image.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {
    List<Image> findByPostId(Long postId);

    @Modifying
    @Query("DELETE FROM Image i WHERE i.post.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);

}
