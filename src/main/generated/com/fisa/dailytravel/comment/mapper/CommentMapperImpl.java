package com.fisa.dailytravel.comment.mapper;

import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-02T13:48:03+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentRequestToComment(CommentRequest commentRequest, Post post, User user) {
        if ( commentRequest == null && post == null && user == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        if ( commentRequest != null ) {
            comment.content( commentRequest.getContent() );
        }
        comment.post( post );
        comment.user( user );

        return comment.build();
    }

    @Override
    public CommentResponse commentToCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse.CommentResponseBuilder commentResponse = CommentResponse.builder();

        commentResponse.postId( commentPostId( comment ) );
        commentResponse.usersId( commentUserId( comment ) );
        commentResponse.nickname( commentUserNickname( comment ) );
        commentResponse.profileImagePath( commentUserProfileImagePath( comment ) );
        commentResponse.content( comment.getContent() );
        commentResponse.createdAt( comment.getCreatedAt() );
        commentResponse.updatedAt( comment.getUpdatedAt() );
        commentResponse.id( comment.getId() );

        return commentResponse.build();
    }

    private Long commentPostId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Post post = comment.getPost();
        if ( post == null ) {
            return null;
        }
        Long id = post.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long commentUserId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String commentUserNickname(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String nickname = user.getNickname();
        if ( nickname == null ) {
            return null;
        }
        return nickname;
    }

    private String commentUserProfileImagePath(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String profileImagePath = user.getProfileImagePath();
        if ( profileImagePath == null ) {
            return null;
        }
        return profileImagePath;
    }
}
