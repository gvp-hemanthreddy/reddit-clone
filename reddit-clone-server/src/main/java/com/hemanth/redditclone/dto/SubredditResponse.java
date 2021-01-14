package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubredditResponse {
    private String name;
    private String title;
    private String description;
    private String username;
    private Instant createdAt;
    private List<PostResponse> posts;
}
