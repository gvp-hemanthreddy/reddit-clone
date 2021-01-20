package com.hemanth.redditclone.mapper;

import com.hemanth.redditclone.dto.PostRequest;
import com.hemanth.redditclone.dto.PostResponse;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.model.Vote;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "postRequest.id", target = "id")
    @Mapping(source = "postRequest.title", target = "title")
    @Mapping(source = "postRequest.body", target = "body")
    @Mapping(source = "subreddit", target = "subreddit")
    @Mapping(source = "currentUser", target = "user")
    @Mapping(target = "identifier", expression = "java(com.hemanth.redditclone.util.Utils.makeId(7))")
    @Mapping(target = "slug", expression = "java(com.hemanth.redditclone.util.Utils.slugify(postRequest.getTitle()))")
    @Mapping(target = "comments", expression = "java(null)")
    @Mapping(target = "votes", expression = "java(null)")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    Post mapToPost(PostRequest postRequest, Subreddit subreddit, User currentUser);

    @Mapping(source = "post.identifier", target = "identifier")
    @Mapping(source = "post.title", target = "title")
    @Mapping(source = "post.body", target = "body")
    @Mapping(source = "post.slug", target = "slug")
    @Mapping(source = "post.createdAt", target = "createdAt")
    @Mapping(source = "subreddit.name", target = "subreddit")
    @Mapping(target = "username", expression = "java(post.getUser().getUsername())")
    @Mapping(target = "comments", expression = "java(null)")
    @Mapping(target = "url", expression = "java(null)")
    @Mapping(target = "userVote", expression = "java(0)")
    @Mapping(target = "voteScore", expression = "java(0)")
    @Mapping(target = "commentCount", expression = "java(0)")
    PostResponse mapToDto(Post post, Subreddit subreddit, User currentUser);

    @AfterMapping
    default void setPostUrlAndCount(@MappingTarget PostResponse postResponse, Post post, User user) {
        Subreddit subreddit = post.getSubreddit();
        postResponse.setUrl("/r/" + subreddit.getName() + "/" + post.getIdentifier() + "/" + post.getSlug());
        List<Vote> votes = post.getVotes();
        if (votes != null) {
            int voteScore = 0;
            // TODO: try to use stream reduce here
            for (int i = 0; i < votes.size(); i++) {
                voteScore += votes.get(i).getValue();
                if (user != null && user.equals(votes.get(i).getUser())) {
                    postResponse.setUserVote(votes.get(i).getValue());
                }
            }
            postResponse.setVoteScore(voteScore);
        }
        if (post.getComments() != null) {
            postResponse.setCommentCount(post.getComments().size());
        }
    }
}
