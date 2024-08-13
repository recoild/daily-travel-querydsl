package com.fisa.dailytravel.comment.controller;

import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {

 //   private final PostService postService;
    private final CommentService commentService;

    // 해당 게시글의 글 작성
    @PostMapping("/v1/comments")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest commentRequest, JwtAuthenticationToken principal) { // id -> commentsId

        log.info("comment insert start!");
        try {
            // JwtAuthenticationToken principal, principal.getName()
            CommentResponse comment = commentService.createComment(commentRequest, principal);
            log.info("comment insert end!");
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 해당 게시글의 댓글 목록 조회
    @GetMapping("v1/comments/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable Long postId) {
        try {
 //           postId = postService.getPost(postId).getId();
            List<CommentResponse> comments = commentService.getAllComments(postId);
            return ResponseEntity.status(HttpStatus.OK).body(comments);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 해당 게시글의 댓글 삭제
    @DeleteMapping("/v1/comments")
    public ResponseEntity<CommentResponse> deleteComment(@RequestParam Long postId, @RequestParam("id") Long id) { // id -> commentsId
        try {
//            postId = postService.getPost(postId).getPostId();
            commentService.deleteComment(postId, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
