//package com.fisa.dailytravel.comment.controller;
//
//import java.util.List;
//
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fisa.dailytravel.comment.dto.CommentListResponse;
//import com.fisa.dailytravel.comment.dto.CommentPageRequest;
//import com.fisa.dailytravel.comment.dto.CommentRequest;
//import com.fisa.dailytravel.comment.dto.CommentResponse;
//import com.fisa.dailytravel.comment.service.CommentService;
//import com.fisa.dailytravel.global.dto.ApiResponse;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//public class CommentController {
//    private final CommentService commentService;
//
//    // 해당 게시글의 댓글 작성
//    @PostMapping("/v1/comments")
//    public ApiResponse<String> saveComment(@RequestBody CommentRequest commentRequest, JwtAuthenticationToken principal) {
//        String uuid = principal.getName();
//        return ApiResponse.ok(commentService.saveComment(uuid, commentRequest));
//    }
//
//    // 해당 게시글의 댓글 목록 조회
//    @GetMapping("v1/comments/{postId}")
//    public ApiResponse<CommentListResponse> getComments(@PathVariable("postId") Long postId, @ModelAttribute CommentPageRequest commentPageRequest) {
//
//        List<CommentResponse> comments = commentService.getComments(postId, commentPageRequest);
//        return ApiResponse.ok(CommentListResponse.builder().comments(comments).build());
//    }
//
//    // 해당 게시글의 댓글 삭제
//    @DeleteMapping("/v1/comments")
//    public ApiResponse<String> deleteComment(@RequestBody CommentRequest commentRequest, JwtAuthenticationToken principal) {
//        String uuid = principal.getName();
//        return ApiResponse.ok(commentService.deleteComment(uuid, commentRequest));
//    }
//}
