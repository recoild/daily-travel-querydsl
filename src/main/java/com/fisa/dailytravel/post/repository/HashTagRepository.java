package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<Hashtag, Long> {

}
