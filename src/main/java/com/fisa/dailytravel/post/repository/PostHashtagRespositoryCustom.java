package com.fisa.dailytravel.post.repository;

import com.fisa.dailytravel.post.models.Hashtag;
import com.fisa.dailytravel.post.models.PostHashtag;

import java.util.List;

public interface PostHashtagRespositoryCustom {
    List<PostHashtag> findAllByHashtags(List<Hashtag> hashtagIds);
}
