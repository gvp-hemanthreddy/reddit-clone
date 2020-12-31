package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {
    private Long id;
    private String title;
    private String body;
    private String subreddit;
}
