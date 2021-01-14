package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.PostResponse;
import com.hemanth.redditclone.dto.SubredditRequest;
import com.hemanth.redditclone.dto.SubredditResponse;
import com.hemanth.redditclone.exceptions.ApiRequestException;
import com.hemanth.redditclone.mapper.PostMapper;
import com.hemanth.redditclone.mapper.SubredditMapper;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.repository.PostRepository;
import com.hemanth.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final SubredditMapper subredditMapper;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public List<SubredditResponse> getAllSubreddits() {
        return subredditRepository.findAll()
                .stream()
                .map(subreddit -> subredditMapper.mapToDto(subreddit, subreddit.getUser()))
                .collect(Collectors.toList());
    }

    @Transactional
    public SubredditResponse createSubreddit(SubredditRequest subredditRequest) {
        if (!StringUtils.hasText(subredditRequest.getName())) {
            throw new ApiRequestException("Subreddit name is Empty");
        }

        if (StringUtils.containsWhitespace(subredditRequest.getName())) {
            throw new ApiRequestException("Subreddit name must not contain white space character");
        }

        if (!StringUtils.hasText(subredditRequest.getTitle())) {
            throw new ApiRequestException("Subreddit title is Empty");
        }

        if (subredditRepository.existsByName(subredditRequest.getName().toLowerCase())) {
            throw new ApiRequestException("Subreddit name already exists");
        }

        User user = authService.getCurrentUser();
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapToSubreddit(subredditRequest, user));
        return subredditMapper.mapToDto(subreddit, subreddit.getUser());
    }

    @Transactional(readOnly = true)
    public SubredditResponse getSubredditByName(String name) {
        Subreddit subreddit = subredditRepository.findByName(name)
                .orElseThrow(() -> new ApiRequestException("Subreddit not found with name - " + name));

        List<Post> posts = postRepository.findBySubredditOrderByCreatedAtDesc(subreddit);
        List<PostResponse> postResponses = posts.stream()
                .map(post -> postMapper.mapToDto(post, subreddit, authService.getCurrentUser()))
                .collect(Collectors.toList());

        SubredditResponse subredditResponse = subredditMapper.mapToDto(subreddit, subreddit.getUser());
        subredditResponse.setPosts(postResponses);

        return subredditResponse;
    }
}
