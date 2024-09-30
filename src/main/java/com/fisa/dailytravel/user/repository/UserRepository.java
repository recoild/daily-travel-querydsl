package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
//    Optional<User> findByUuid(String uuid);

//    @Query("SELECT p " +
//            "FROM Post p " +
//            "WHERE p.user.uuid = :uuid " +
//            "ORDER BY p.createdAt DESC")
//    Page<Post> findLatestPostsByUuid(@Param("uuid") String uuid, Pageable pageable);
}
