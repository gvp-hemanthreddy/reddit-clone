package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.PostRequest;
import com.hemanth.redditclone.dto.PostResponse;
import com.hemanth.redditclone.exceptions.SpringRedditException;
import com.hemanth.redditclone.mapper.PostMapper;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.repository.PostRepository;
import com.hemanth.redditclone.repository.SubredditRepository;
import com.hemanth.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        Subreddit subreddit = subredditRepository
                .findByName(postRequest.getSubreddit())
                .orElseThrow(() -> new SpringRedditException("Subreddit not found with name - " + postRequest.getSubreddit()));
        Post post = postRepository.save(postMapper.mapToPost(postRequest, subreddit, authService.getCurrentUser()));
        return postMapper.mapToDto(post, subreddit);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> postMapper.mapToDto(post, post.getSubreddit()))
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new SpringRedditException("Post not found with id - " + postId));
        return postMapper.mapToDto(post, post.getSubreddit());
    }

    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SpringRedditException("Subreddit not found with id - " + subredditId));
        return postRepository.findBySubreddit(subreddit)
                .stream()
                .map(post -> postMapper.mapToDto(post, subreddit))
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
        return postRepository.findByUser(user)
                .stream()
                .map(post -> postMapper.mapToDto(post, post.getSubreddit()))
                .collect(Collectors.toList());
    }
}
