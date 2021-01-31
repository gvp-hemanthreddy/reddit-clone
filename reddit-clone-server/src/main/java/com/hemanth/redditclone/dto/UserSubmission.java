package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserSubmission {
    private String type;
    private Object submission;
}
