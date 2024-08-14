package com.fisa.dailytravel.comment.controller;

import com.fisa.dailytravel.comment.dto.CommentListResponse;
import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.comment.service.CommentService;
import com.fisa.dailytravel.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 해당 게시글의 글 작성
    @PostMapping("/v1/comments")
    public ApiResponse<String> createComment(@RequestBody CommentRequest commentRequest, JwtAuthenticationToken principal) { // id -> commentsId

        log.info("comment insert start!");
        try {
            String uuid = principal.getName();
            return ApiResponse.ok(commentService.createComment(uuid, commentRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("댓글 작성 실패");
        }
    }

    // 해당 게시글의 댓글 목록 조회
    @GetMapping("v1/comments/{postId}")
    public ApiResponse<CommentListResponse> getPageComments(@PathVariable("postId") Long postId, @RequestBody CommentPageRequest commentPageRequest, JwtAuthenticationToken principal) {
        try {
            List<CommentResponse> comments = commentService.getPageComments(postId, commentPageRequest);

            return ApiResponse.ok(CommentListResponse.builder().comments(comments).build());
//            List<Comment> comments = commentService.getPageComments(postId, commentPageRequest).getContent();
//            return ApiResponse.ok(comments);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 해당 게시글의 댓글 삭제
    @DeleteMapping("/v1/comments")
    public ApiResponse<String> deleteComment(@RequestBody CommentRequest commentRequest, JwtAuthenticationToken principal) {
        try {
            String uuid = principal.getName();
            return ApiResponse.ok(commentService.deleteComment(uuid, commentRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("댓글 삭제 실패");
        }
    }

}
