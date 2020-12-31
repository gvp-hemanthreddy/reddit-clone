package com.hemanth.redditclone.dto;

import com.hemanth.redditclone.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
