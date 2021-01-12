package com.hemanth.redditclone.controller;

import com.hemanth.redditclone.dto.AuthenticationResponse;
import com.hemanth.redditclone.dto.LoginRequest;
import com.hemanth.redditclone.dto.RefreshTokenRequest;
import com.hemanth.redditclone.dto.RegisterRequest;
import com.hemanth.redditclone.service.AuthService;
import com.hemanth.redditclone.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registration successful. Please check email to activate account");
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) {
        authService.verifyAccount(token);
        return ResponseEntity
                .status(OK)
                .body("Account Activated Successfully");
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("refresh")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity
                .status(CREATED)
                .body(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("logout")
    public ResponseEntity logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity
                .status(OK)
                .body("Logged out successfully");
    }
}
