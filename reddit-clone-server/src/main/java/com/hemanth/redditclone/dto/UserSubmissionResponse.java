package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class UserSubmissionResponse {
    private String username;
    private Instant createdAt;
    private List<UserSubmission> submissions;
}
