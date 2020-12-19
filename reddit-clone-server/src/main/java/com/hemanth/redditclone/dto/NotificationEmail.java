package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationEmail {
    @NotBlank
    private String subject;

    @NotBlank
    private String recipient;

    @NotBlank
    private String body;
}
