package com.fisa.dailytravel.comment.service;

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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public CommentResponse createComment(String uuid, CommentRequest commentRequest) {//CommentRequest(id=1, content=룰루랄라3)

        User user = userRepository.findByUuid(uuid);

        Post post = postRepository.findById(commentRequest.getId()).get();
    //            .orElseThrow(() -> new EntityNotFoundException("Post not found"));

//        // 인증된 사용자 정보 추출
//        String uuid = principal.getName(); // JWT에서 사용자 UUID를 가져옵니다.
        log.info("Authenticated UUID: " + uuid);

        // UserRepository를 사용하여 실제 User 객체를 조회
//        User user = userRepository.findByUuid(uuid); // UUID로 사용자 조회

        // CommentRequest를 Comment 엔티티로 매핑
        Comment comment = modelMapper.map(commentRequest, Comment.class);

        System.out.println("---- "+ comment); // ---- Comment(id=1, post=null, content=룰루랄라3, createdAt=null, updatedAt=null, user=null)
        comment.setUser(user); // 실제 User 객체를 설정합니다.
        comment.setPost(post); // 게시글 설정
//        comment.setCreatedAt(comment.getCreatedAt()); // 생성 날짜 설정
        System.out.println("--**-- "+ comment); // ---- Comment(id=1, post=null, content=룰루랄라3, createdAt=null, updatedAt=null, user=null)

        // 댓글 저장
        Comment savedComment = commentRepository.save(comment);

        // CommentResponse로 매핑하여 반환
        return modelMapper.map(savedComment, CommentResponse.class);
    }

    // Read
    @Override
    public List<CommentResponse> getAllComments(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAscIdAsc(postId).stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class))
                .collect(Collectors.toList());
    }

    // Delete
    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        if (!comment.getPost().getId().equals(postId)) {
            throw new RuntimeException("Comment does not belong to the given post");
        }
        commentRepository.delete(comment);
    }


//    // Create
//    @Transactional
//    @Override
//    public CommentResponse createComment(Long postId, CommentRequest commentRequest) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
//        Long usersId = commentRequest.getUsersId();
//        // CommentRequest를 Comment 엔티티로 매핑합니다.
//        Comment comment = autoMapper.map(commentRequest, Comment.class);
//        comment.setUser(usersId); // ID만 설정한 User 엔티티를 설정합니다.
//        comment.setPost(post); // ID만 설정한 Post 엔티티를 설정합니다.
//        comment.setCreatedAt(LocalDate.now()); // 생성 날짜를 설정합니다.
//        Comment savedComment = commentRepository.save(comment);
//        return autoMapper.map(savedComment, CommentResponse.class);
//    }
//
//    // Read
//    @Override
//    public List<CommentResponse> getAllComments(Long postId) {
//        return commentRepository.findByPostIdOrderByCreatedAtAscIdAsc(postId).stream()
//                .map(comment -> autoMapper.map(comment, CommentResponse.class))
//                .collect(Collectors.toList());
//    }
//
//    // Delete
//    @Transactional
//    @Override
//    public void deleteComment(Long postId, Long commentId) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
//        if (!comment.getPost().getId().equals(postId)) {
//            throw new RuntimeException("Comment does not belong to the given post");
//        }
//        commentRepository.delete(comment);
//    }
}
