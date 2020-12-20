package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.SubredditDto;
import com.hemanth.redditclone.exceptions.SpringRedditException;
import com.hemanth.redditclone.mapper.SubredditMapper;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final SubredditMapper subredditMapper;

    @Transactional(readOnly = true)
    public List<SubredditDto> getAllSubreddits() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("Subreddit not found with id - " + id));
        return subredditMapper.mapToDto(subreddit);
    }

    @Transactional
    public SubredditDto createSubreddit(SubredditDto subredditDto) {
        User user = authService.getCurrentUser();
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapToSubreddit(subredditDto, user));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }
}
