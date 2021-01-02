package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.VoteRequest;
import com.hemanth.redditclone.exceptions.ApiRequestException;
import com.hemanth.redditclone.mapper.VoteMapper;
import com.hemanth.redditclone.model.Comment;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.model.Vote;
import com.hemanth.redditclone.repository.CommentRepository;
import com.hemanth.redditclone.repository.PostRepository;
import com.hemanth.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private VoteRepository voteRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private AuthService authService;
    private VoteMapper voteMapper;

    private static final List<Integer> validValues = Arrays.asList(0, 1, -1);

    public void vote(VoteRequest voteRequest) {
        int value = voteRequest.getValue();
        String postIdentifier = voteRequest.getPostIdentifier();
        String commentIdentifier = voteRequest.getCommentIdentifier();

        if (!validValues.contains(value)) {
            throw new ApiRequestException("Vote value should be 1, 0 or -1");
        }

        User currentUser = authService.getCurrentUser();

        Post post = null;
        Comment comment = null;
        Vote vote = null;

        if (commentIdentifier != null) {
            Optional<Comment> optionalComment = commentRepository.findByIdentifier(commentIdentifier);
            if (optionalComment.isPresent()) {
                comment = optionalComment.get();
                Optional<Vote> optionalVote = voteRepository.findByCommentAndUser(comment, currentUser);
                vote = optionalVote.isPresent() ? optionalVote.get() : null;
            }
        } else {
            Optional<Post> optionalPost = postRepository.findByIdentifier(postIdentifier);
            if (optionalPost.isPresent()) {
                post = optionalPost.get();
                Optional<Vote> optionalVote = voteRepository.findByPostAndUser(post, currentUser);
                vote = optionalVote.isPresent() ? optionalVote.get() : null;
            }
        }

        if (vote == null && value == 0) {
            throw new ApiRequestException("Invalid vote value");
        }

        if (vote == null) {
            voteRepository.save(voteMapper.mapToVote(voteRequest, post, comment, currentUser));
        } else if (value == 0) {
            // remove vote
            voteRepository.delete(vote);
        } else if (vote.getValue() != value) {
            vote.setValue(value);
            voteRepository.save(vote);
        }
    }
}
