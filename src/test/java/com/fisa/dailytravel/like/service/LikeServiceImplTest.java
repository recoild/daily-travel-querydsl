package com.fisa.dailytravel.like.service;

import com.fisa.dailytravel.like.dto.LikeResponse;
import com.fisa.dailytravel.post.dto.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LikeServiceImplTest {

    @Autowired
    LikeService likeService;

    /**
     * 즐겨찾기 (좋아요 누른) 게시물 가져오기
     */
    @Test
    void favoriteGetPostList(){
        //given
        String uuid = "111969318487959339341";
        int page = 0;
        int count = 10;

        //when
        LikeResponse postResponses = likeService.favoritePosts(uuid, page, count);

        //then
        Assertions.assertThat(postResponses.getPostPreviewResponses()).isNotEmpty();
        Assertions.assertThat(postResponses.getPostPreviewResponses().size()).isEqualTo(10);
    }





    /**
     * 좋아요를 안 눌렀으면 좋아요 등록
     */
    @Test
    void likeToggleLike(){
        //given
        String uuid = "111969318487959339341";
        Long postId = 11L;

        //when
        Boolean like = likeService.likeToggle(postId, uuid);

        //then
        Assertions.assertThat(like).isTrue();
    }

    /**
     * 좋아요 눌렀으면 좋아요 삭제
     */
    @Test
    void likeToggleLikeDelete(){
        //given
        String uuid = "111969318487959339341";
        Long postId = 11L;

        //when
        Boolean like = likeService.likeToggle(postId, uuid);

        //then
        Assertions.assertThat(like).isFalse();
    }

}