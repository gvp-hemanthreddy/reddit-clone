package com.hemanth.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum VoteType {
    DOWNVOTE(-1),
    UPVOTE(1);

    @Getter
    private int direction;
}
