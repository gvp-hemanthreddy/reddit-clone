package com.hemanth.redditclone.controller;

import com.hemanth.redditclone.dto.SubredditRequest;
import com.hemanth.redditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/subreddit")
@AllArgsConstructor
public class SubredditController {
    private SubredditService subredditService;

    @GetMapping
    public ResponseEntity getAllSubreddits() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getAllSubreddits());
    }

    @GetMapping("{name}")
    public ResponseEntity getSubredditByName(@PathVariable String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getSubredditByName(name));
    }

    @PostMapping
    public ResponseEntity createSubreddit(@RequestBody SubredditRequest subredditRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subredditService.createSubreddit(subredditRequest));
    }
}
