package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.Hashtag;

import java.util.List;
import java.util.Set;

public interface HashTagRepositoryCustom {
    List<String> findExistingHashtags(List<String> hashtags);
}
