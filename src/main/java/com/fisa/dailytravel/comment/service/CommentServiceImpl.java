package com.fisa.dailytravel.comment.service;

import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.comment.repository.CommentRepository;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public String createComment(String uuid, CommentRequest commentRequest) {
        User user = userRepository.findByUuid(uuid);
        Post post = postRepository.findById(commentRequest.getId()).get();

        log.info("Authenticated UUID: " + uuid);

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);

        return "댓글 저장 완료";
    }

    @Override
    public List<CommentResponse> getPageComments(Long postId, CommentPageRequest commentPageRequest) {
        Pageable pageable = PageRequest.of(commentPageRequest.getPage(), commentPageRequest.getCount());

        Page<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAscIdAsc(postId, pageable);
        List<CommentResponse> commentResponses = comments.stream().map(comment -> CommentResponse.of(comment)).collect(Collectors.toList());

        return commentResponses;
    }

    @Override
    public String deleteComment(String uuid, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentRequest.getId()).get();

        commentRepository.delete(comment);

        return "댓글 삭제 완료";
    }
}
