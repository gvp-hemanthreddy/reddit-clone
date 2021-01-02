package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteRequest {
    private Long id;
    private String postIdentifier;
//    private String slug;
    private String commentIdentifier;
    private int value;
}
