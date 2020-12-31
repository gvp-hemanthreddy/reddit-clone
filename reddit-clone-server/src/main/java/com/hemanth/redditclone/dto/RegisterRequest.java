package com.hemanth.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {
    @Email(message = "Must be a valid email address")
    private String email;

    @Length(min = 3, max = 255, message = "Must be at least 3 characters long")
    private String username;

    @Length(min = 6, max = 255, message = "Must be at least 6 characters long")
    private String password;
}
