package com.hemanth.redditclone.mapper;

import com.hemanth.redditclone.dto.CommentRequest;
import com.hemanth.redditclone.dto.CommentResponse;
import com.hemanth.redditclone.model.Comment;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "commentRequest.id", target = "id")
    @Mapping(source = "commentRequest.content", target = "content")
    @Mapping(source = "post", target = "post")
    @Mapping(source = "user", target = "user")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    Comment mapToComment(CommentRequest commentRequest, Post post, User user);

    @Mapping(source = "comment.id", target = "id")
    @Mapping(source = "comment.content", target = "content")
    @Mapping(target = "postId", expression = "java(comment.getPost().getId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentResponse mapToDto(Comment comment);
}
