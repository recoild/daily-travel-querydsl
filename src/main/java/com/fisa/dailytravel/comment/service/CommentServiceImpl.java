package com.fisa.dailytravel.comment.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.mapper.CommentMapper;
import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.comment.repository.CommentRepository;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public String saveComment(String uuid, CommentRequest commentRequest) {
        User user = userRepository.findByUuid(uuid);
        Post post = postRepository.findById(commentRequest.getId()).get();

        log.info("Authenticated UUID: " + uuid);
        Comment comment = commentMapper.commentRequestToComment(commentRequest, post, user); // CommentRequest를 Comment로 변환 : commentRequest와 post, user를 매핑하여 Comment 객체 생성
        commentRepository.saveAndFlush(comment); //  데이터베이스와 동기화를 위해 saveAndFlush() 사용
        entityManager.refresh(comment); // 엔티티를 데이터베이스에서 다시 읽어와서 엔티티를 갱신 -> 데이터베이스에서 자동으로 설정된 필드 값들이 엔티티에 반영
        log.info("Comment : " + comment);

        return "댓글 저장 완료";
    }

    @Override
    public List<CommentResponse> getComments(Long postId, CommentPageRequest commentPageRequest) {
        Pageable pageable = PageRequest.of(commentPageRequest.getPage(), commentPageRequest.getCount());

        log.info("Requested Page: " + commentPageRequest.getPage()); // 요청된 페이지
        log.info("Page Size: " + commentPageRequest.getCount()); // 페이지 크기

        Page<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAscIdAsc(postId, pageable);

        log.info("Total Elements: " + comments.getTotalElements()); // 전체 요소 수
        log.info("Total Pages: " + comments.getTotalPages()); // 전체 페이지 수

        return comments.getContent().stream()
                .map(commentMapper::commentToCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteComment(String uuid, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentRequest.getId()).get();
        commentRepository.delete(comment);

        return "댓글 삭제 완료";
    }
}
