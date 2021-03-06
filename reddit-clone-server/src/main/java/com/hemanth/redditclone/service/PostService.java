package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.PostRequest;
import com.hemanth.redditclone.dto.PostResponse;
import com.hemanth.redditclone.exceptions.ApiRequestException;
import com.hemanth.redditclone.mapper.PostMapper;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.repository.PostRepository;
import com.hemanth.redditclone.repository.SubredditRepository;
import com.hemanth.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public PostResponse createPost(PostRequest postRequest) {
        if (!StringUtils.hasText(postRequest.getTitle())) {
            throw new ApiRequestException("Post title is empty");
        }

        if (!StringUtils.hasText(postRequest.getSubreddit())) {
            throw new ApiRequestException("Post subreddit is empty");
        }

        Subreddit subreddit = subredditRepository
                .findByName(postRequest.getSubreddit())
                .orElseThrow(() -> new ApiRequestException("Subreddit not found with name - " + postRequest.getSubreddit()));
        Post post = postRepository.save(postMapper.mapToPost(postRequest, subreddit, authService.getCurrentUser()));
        return postMapper.mapToDto(post, subreddit, post.getUser());
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(post -> postMapper.mapToDto(post, post.getSubreddit(), authService.getCurrentUser()))
                .collect(Collectors.toList());
    }

    public PostResponse getPostByIdentifier(String identifier) {
        Post post = postRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new ApiRequestException("Post not found with identifier - " + identifier));
        return postMapper.mapToDto(post, post.getSubreddit(), authService.getCurrentUser());
    }

    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiRequestException("User not found with name - " + username));
        return postRepository.findByUser(user)
                .stream()
                .map(post -> postMapper.mapToDto(post, post.getSubreddit(), authService.getCurrentUser()))
                .collect(Collectors.toList());
    }
}
