package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.VoteDto;
import com.hemanth.redditclone.exceptions.ApiRequestException;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.Vote;
import com.hemanth.redditclone.model.VoteType;
import com.hemanth.redditclone.repository.PostRepository;
import com.hemanth.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private VoteRepository voteRepository;
    private PostRepository postRepository;
    private AuthService authService;

    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new ApiRequestException("Post not found with id - " + voteDto.getPostId()));
        Optional<Vote> vote = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());
        if (vote.isPresent() &&
            vote.get().getVoteType() == voteDto.getVoteType()) {
            throw new ApiRequestException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
//            post.
        } else {

        }
    }
}
