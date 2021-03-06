package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse {
    private String identifier;
    private String title;
    private String body;
    private String subreddit;
    private String username;
    private String slug;
    private String url;
    private int userVote;
    private int voteScore;
    private int commentCount;
    private Instant createdAt;
    private List<CommentResponse> comments;
}
