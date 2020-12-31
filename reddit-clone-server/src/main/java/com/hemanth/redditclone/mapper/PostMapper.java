package com.hemanth.redditclone.mapper;

import com.hemanth.redditclone.dto.PostRequest;
import com.hemanth.redditclone.dto.PostResponse;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "postRequest.id", target = "id")
    @Mapping(source = "postRequest.title", target = "title")
    @Mapping(source = "postRequest.body", target = "body")
    @Mapping(source = "subreddit", target = "subreddit")
    @Mapping(source = "currentUser", target = "user")
    @Mapping(target = "identifier", expression = "java(com.hemanth.redditclone.util.Utils.makeId(7))")
    @Mapping(target = "slug", expression = "java(com.hemanth.redditclone.util.Utils.slugify(postRequest.getTitle()))")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    Post mapToPost(PostRequest postRequest, Subreddit subreddit, User currentUser);

    @Mapping(source = "post.identifier", target = "identifier")
    @Mapping(source = "post.title", target = "title")
    @Mapping(source = "post.body", target = "body")
    @Mapping(source = "post.slug", target = "slug")
    @Mapping(source = "post.createdAt", target = "createdAt")
    @Mapping(source = "subreddit.name", target = "subreddit")
    @Mapping(source = "currentUser.username", target = "username")
    PostResponse mapToDto(Post post, Subreddit subreddit, User currentUser);

    @AfterMapping
    default void setPostUrl(@MappingTarget PostResponse postResponse, Post post) {
        Subreddit subreddit = post.getSubreddit();
        postResponse.setUrl("r/" + subreddit.getName() + "/" + post.getIdentifier() + "/" + post.getSlug());
    }
}
