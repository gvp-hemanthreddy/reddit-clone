package com.hemanth.redditclone.mapper;

import com.hemanth.redditclone.dto.PostRequest;
import com.hemanth.redditclone.dto.PostResponse;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "postRequest.id", target = "id")
    @Mapping(source = "postRequest.name", target = "name")
    @Mapping(source = "postRequest.description", target = "description")
    @Mapping(source = "subreddit", target = "subreddit")
    @Mapping(source = "currentUser", target = "user")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    Post mapToPost(PostRequest postRequest, Subreddit subreddit, User currentUser);

    @Mapping(source = "post.id", target = "id")
    @Mapping(source = "post.name", target = "name")
    @Mapping(source = "post.description", target = "description")
    @Mapping(source = "subreddit.name", target = "subreddit")
    PostResponse mapToDto(Post post, Subreddit subreddit);
}
