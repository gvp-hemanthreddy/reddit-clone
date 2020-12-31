package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.SubredditRequest;
import com.hemanth.redditclone.dto.SubredditResponse;
import com.hemanth.redditclone.exceptions.ApiRequestException;
import com.hemanth.redditclone.mapper.SubredditMapper;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
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
    private final AuthService authService;
    private final SubredditMapper subredditMapper;

    @Transactional(readOnly = true)
    public List<SubredditResponse> getAllSubreddits() {
        return subredditRepository.findAll()
                .stream()
                .map(subreddit -> subredditMapper.mapToDto(subreddit, subreddit.getUser()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditResponse getSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Subreddit not found with id - " + id));
        return subredditMapper.mapToDto(subreddit, subreddit.getUser());
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
}
