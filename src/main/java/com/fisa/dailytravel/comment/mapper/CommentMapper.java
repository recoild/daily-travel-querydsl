package com.fisa.dailytravel.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.fisa.dailytravel.comment.dto.CommentRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.user.models.User;

@Mapper(componentModel = "spring", typeConversionPolicy = ReportingPolicy.ERROR)
public interface CommentMapper {

    /**
     * CommentRequest를 Comment로 변환
     * source : 정보를 제공하는 객체
     * target : 정보를 받을 객체
     */
    @Mapping(source = "commentRequest.content", target = "content")
    @Mapping(source = "post", target = "post") // post 파라미터를 Comment의 post에 매핑
    @Mapping(source = "user", target = "user") // user 파라미터를 Comment의 user에 매핑
    @Mapping(target = "id", ignore = true) // id : 댓글 식별자는 자동 생성되어 무시
    @Mapping(target = "createdAt", ignore = true) // createdAt : 댓글 작성 시간은 자동 생성되어 무시
    @Mapping(target = "updatedAt", ignore = true) // updatedAt : 댓글 수정 시간은 자동 생성되어 무시
    Comment commentRequestToComment(CommentRequest commentRequest, Post post, User user);

    /**
     * Comment를 CommentResponse로 변환
     * source : 정보를 제공하는 객체
     * target : 정보를 받을 객체
     */
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "usersId")
    @Mapping(source = "user.nickname", target = "nickname")
    @Mapping(source = "user.profileImagePath", target = "profileImagePath")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    CommentResponse commentToCommentResponse(Comment comment);
}
