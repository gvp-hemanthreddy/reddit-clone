package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.CommentRequest;
import com.hemanth.redditclone.dto.CommentResponse;
import com.hemanth.redditclone.exceptions.ApiRequestException;
import com.hemanth.redditclone.mapper.CommentMapper;
import com.hemanth.redditclone.model.Comment;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.repository.CommentRepository;
import com.hemanth.redditclone.repository.PostRepository;
import com.hemanth.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final AuthService authService;

    public CommentResponse createCommentOnPost(String identifier, CommentRequest commentRequest) {
        Post post = postRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new ApiRequestException("Post not found with Identifier - " + identifier));
        Comment comment = commentRepository.save(commentMapper.mapToComment(commentRequest, post, authService.getCurrentUser()));
        return commentMapper.mapToDto(comment);
    }

    public List<CommentResponse> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiRequestException("Post not found with id - " + postId));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiRequestException("User not found with username - " + username));
        return commentRepository.findByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
