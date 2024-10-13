package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.Hashtag;

import java.util.List;

public interface HashTagRepositoryCustom {
    List<Hashtag> findExistingHashtags(List<String> hashtags);
}
