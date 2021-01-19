package com.hemanth.redditclone.mapper;

import com.hemanth.redditclone.dto.CommentRequest;
import com.hemanth.redditclone.dto.CommentResponse;
import com.hemanth.redditclone.model.Comment;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.model.Vote;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "commentRequest.id", target = "id")
    @Mapping(source = "commentRequest.body", target = "body")
    @Mapping(target = "identifier", expression = "java(com.hemanth.redditclone.util.Utils.makeId(8))")
    @Mapping(source = "post", target = "post")
    @Mapping(source = "user", target = "user")
    @Mapping(target = "votes", expression = "java(null)")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    Comment mapToComment(CommentRequest commentRequest, Post post, User user);

    @Mapping(source = "comment.identifier", target = "identifier")
    @Mapping(source = "comment.body", target = "body")
    @Mapping(target = "postIdentifier", expression = "java(comment.getPost().getIdentifier())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    @Mapping(source = "comment.createdAt", target = "createdAt")
    @Mapping(target = "userVote", expression = "java(0)")
    @Mapping(target = "voteScore", expression = "java(0)")
    CommentResponse mapToDto(Comment comment, User currentUser);

    @AfterMapping
    default void setVoteScoreAndUserVote(@MappingTarget CommentResponse commentResponse, Comment comment, User user) {
        List<Vote> votes = comment.getVotes();
        if (votes != null) {
            int voteScore = 0;
            // TODO: try to use stream reduce here
            for (int i = 0; i < votes.size(); i++) {
                voteScore += votes.get(i).getValue();
                if (user != null && user.equals(votes.get(i).getUser())) {
                    commentResponse.setUserVote(votes.get(i).getValue());
                }
            }
            commentResponse.setVoteScore(voteScore);
        }
    }
}
