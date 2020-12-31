package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentResponse {
    private Long id;
    private String content;
    private Long postId;
    private String username;
}
