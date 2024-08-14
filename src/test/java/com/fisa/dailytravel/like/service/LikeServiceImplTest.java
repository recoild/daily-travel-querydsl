package com.fisa.dailytravel.like.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LikeServiceImplTest {

    @Autowired
    LikeService likeService;

    /**
     * 즐겨찾기 (좋아요 누른) 게시물 가져오기
     */
    void favoriteGetPostList(){
        //given
        String uuid = "";

        //when
        //likeService.favoritePosts();

        //then
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