package com.hemanth.redditclone.mapper;

import com.hemanth.redditclone.dto.SubredditRequest;
import com.hemanth.redditclone.dto.SubredditResponse;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    SubredditMapper MAPPER = Mappers.getMapper(SubredditMapper.class);

    @Mapping(source = "subredditRequest.name", target = "name")
    @Mapping(source = "subredditRequest.title", target = "title")
    @Mapping(source = "subredditRequest.description", target = "description")
    @Mapping(source = "user", target = "user")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    Subreddit mapToSubreddit(SubredditRequest subredditRequest, User user);

    @Mapping(source = "subreddit.name", target = "name")
    @Mapping(source = "subreddit.title", target = "title")
    @Mapping(source = "subreddit.description", target = "description")
    @Mapping(source = "subreddit.createdAt", target = "createdAt")
    @Mapping(source = "currentUser.username", target = "username")
    SubredditResponse mapToDto(Subreddit subreddit, User currentUser);
}
