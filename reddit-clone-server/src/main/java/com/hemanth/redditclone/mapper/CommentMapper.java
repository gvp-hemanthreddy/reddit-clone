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
    @Mapping(source = "commentRequest.body", target = "body")
    @Mapping(target = "identifier", expression = "java(com.hemanth.redditclone.util.Utils.makeId(8))")
    @Mapping(source = "post", target = "post")
    @Mapping(source = "user", target = "user")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    Comment mapToComment(CommentRequest commentRequest, Post post, User user);

    @Mapping(source = "comment.identifier", target = "identifier")
    @Mapping(source = "comment.body", target = "body")
    @Mapping(target = "postIdentifier", expression = "java(comment.getPost().getIdentifier())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentResponse mapToDto(Comment comment);
}
