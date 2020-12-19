package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private String authenticationToken;
}
