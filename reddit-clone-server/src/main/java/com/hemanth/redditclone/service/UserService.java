package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.CommentResponse;
import com.hemanth.redditclone.dto.PostResponse;
import com.hemanth.redditclone.dto.UserSubmission;
import com.hemanth.redditclone.dto.UserSubmissionResponse;
import com.hemanth.redditclone.exceptions.ApiRequestException;
import com.hemanth.redditclone.mapper.CommentMapper;
import com.hemanth.redditclone.mapper.PostMapper;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.repository.CommentRepository;
import com.hemanth.redditclone.repository.PostRepository;
import com.hemanth.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    private Instant getCreatedAt(UserSubmission submission) {
        if ("post".equals(submission.getType())) {
            return ((PostResponse) submission.getSubmission()).getCreatedAt();
        } else {
            return ((CommentResponse) submission.getSubmission()).getCreatedAt();
        }
    }

    public UserSubmissionResponse getUserSubmissions(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiRequestException("User not found with username - " + username));
        User currentUser = authService.getCurrentUser();
        List<UserSubmission> userSubmissions = new ArrayList<>();
        postRepository
                .findByUser(user)
                .forEach(post -> {
                    PostResponse postResponse = postMapper.mapToDto(post, post.getSubreddit(), currentUser);
                    userSubmissions.add(new UserSubmission("post", postResponse));
                });
        commentRepository
                .findByUser(user)
                .forEach(comment -> {
                    CommentResponse commentResponse = commentMapper.mapToDto(comment, currentUser);
                    userSubmissions.add(new UserSubmission("comment", commentResponse));
                });
//        userSubmissions.sort((submission1, submission2) -> {
//            return getCreatedAt(submission1).compareTo(getCreatedAt(submission2));
//        });
        userSubmissions.sort(Comparator.comparing(this::getCreatedAt));
        return UserSubmissionResponse
                .builder()
                .username(username)
                .createdAt(user.getCreatedAt())
                .submissions(userSubmissions)
                .build();
    }
}
