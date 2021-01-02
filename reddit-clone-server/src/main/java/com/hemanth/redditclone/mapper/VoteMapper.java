package com.hemanth.redditclone.mapper;

import com.hemanth.redditclone.dto.VoteRequest;
import com.hemanth.redditclone.model.Comment;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    @Mapping(source = "voteRequest.id", target = "id")
    @Mapping(source = "voteRequest.value", target = "value")
    @Mapping(source = "post", target = "post")
    @Mapping(source = "comment", target = "comment")
    @Mapping(source = "user", target = "user")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    public Vote mapToVote(VoteRequest voteRequest, Post post, Comment comment, User user);
}
