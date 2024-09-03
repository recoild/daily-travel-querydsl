package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.PostDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostDocRepository extends ElasticsearchRepository<PostDoc, String> {
    List<PostDoc> findByPostContentContainingOrderByCreatedAt(String postContent, Pageable pageable);
}
