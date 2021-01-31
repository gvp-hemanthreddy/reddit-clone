package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentResponse {
    private String identifier;
    private String body;
    private String postIdentifier;
    private String postTitle;
    private String postUrl;
    private String subreddit;
    private String username;
    private int userVote;
    private int voteScore;
    private Instant createdAt;
}
