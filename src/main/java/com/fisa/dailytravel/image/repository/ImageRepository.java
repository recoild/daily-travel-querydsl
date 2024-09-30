package com.fisa.dailytravel.image.repository;

import com.fisa.dailytravel.image.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {
//    List<Image> findByPostId(Long postId);
//
//    void deleteAllByPost(Post post);

//    @Modifying
//    @Query("DELETE FROM Image i WHERE i.post.id = :postId")
//    void deleteByPostId(@Param("postId") Long postId);

}
