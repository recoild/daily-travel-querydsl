package com.fisa.dailytravel.comment.service;

import com.fisa.dailytravel.comment.dto.CommentListResponse;
import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.comment.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fisa.dailytravel.comment.dto.CommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void createComment() {
        // given
        String uuid = "112546345600685256836";
        CommentRequest commentRequest = new CommentRequest(1L, "본문1");

        // when
        String commentResponse = commentService.createComment(uuid, commentRequest);

        // then
        Assertions.assertThat(commentResponse).isEqualTo("댓글 저장 완료");
    }

    @Test
    void getPageComments() {
        // given
        String uuid = "112546345600685256836";

        CommentRequest commentRequest = new CommentRequest(1L, "본문1");
        CommentRequest commentRequest2 = new CommentRequest(1L, "본문2");
        CommentRequest commentRequest3 = new CommentRequest(1L, "본문3");

        commentService.createComment(uuid, commentRequest);
        commentService.createComment(uuid, commentRequest2);
        commentService.createComment(uuid, commentRequest3);

        // when
        // 첫 번째 페이지 : 페이지 번호 0, 페이지 크기 2
        CommentPageRequest commentPageRequest = new CommentPageRequest(0, 2);
        List<CommentResponse> commentListResponse = commentService.getPageComments(1L, commentPageRequest);

        // then
        // 첫 번째 페이지 조회
        Assertions.assertThat(commentListResponse).hasSize(2);
    }

//    @Test
//    void deleteComment() {
//        // given
//        String uuid = "112546345600685256836";
//        CommentRequest commentRequest = new CommentRequest(1L, "본문1");
//        commentService.createComment(uuid, commentRequest);
//
//        // when
//        commentService.deleteComment(uuid, commentRequest);
//
//        // then
//        Assertions.assertThat(commentRepository.findById(1L)).isEmpty();
//    }
}