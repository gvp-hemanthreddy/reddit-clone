package com.hemanth.redditclone.exceptions.apierror;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMap implements ApiSubError {
    private String field;
    private String message;
}
