package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.AuthenticationResponse;
import com.hemanth.redditclone.dto.LoginRequest;
import com.hemanth.redditclone.dto.NotificationEmail;
import com.hemanth.redditclone.dto.RegisterRequest;
import com.hemanth.redditclone.exceptions.ApiRequestException;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.model.VerificationToken;
import com.hemanth.redditclone.repository.UserRepository;
import com.hemanth.redditclone.repository.VerificationTokenRepository;
import com.hemanth.redditclone.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.hemanth.redditclone.util.Constants.ACTIVATION_EMAIL;
import static java.time.Instant.now;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest) {
        if (!StringUtils.hasText(registerRequest.getUsername())) {
            throw new ApiRequestException("username is Empty");
        }

        if (!StringUtils.hasText(registerRequest.getEmail())) {
            throw new ApiRequestException("Email is Empty");
        }

        if (!StringUtils.hasText(registerRequest.getPassword())) {
            throw new ApiRequestException("Password is Empty");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            FieldError fieldError = new FieldError("registerRequest", "email", "Email is already taken");
            throw new ApiRequestException("Validation errors", Collections.singletonList(fieldError));
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            FieldError fieldError = new FieldError("registerRequest", "username", "username is already taken");
            throw new ApiRequestException("Validation errors", Collections.singletonList(fieldError));
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        String subject = "Please activate your account";
        String message = "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                ACTIVATION_EMAIL + token;
        mailService.sendEmail(new NotificationEmail(subject, user.getEmail(), message));
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new ApiRequestException("Invalid Token")));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        Long userId = verificationToken.getUser().getUserId();
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new ApiRequestException("User not found with id - " + userId));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        return token;
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getUsername())) {
            throw new ApiRequestException("username is Empty");
        }

        if (!StringUtils.hasText(loginRequest.getPassword())) {
            throw new ApiRequestException("Password is Empty");
        }

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authenticate);

            String token = jwtProvider.generateToken(authenticate);
            return new AuthenticationResponse(token);
        } catch (DisabledException ex) {
            Map<String, String> errorsMap = new HashMap<>();
            errorsMap.put("loginError", "Please activate your account. Check your email for activation link");
            throw new ApiRequestException("Authentication errors", ex, errorsMap, HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException ex) {
            Map<String, String> errorsMap = new HashMap<>();
            errorsMap.put("loginError", "username or Password is incorrect");
            throw new ApiRequestException("Authentication errors", ex, errorsMap, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            throw new ApiRequestException("Authentication errors", ex);
        }
    }

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found", username)));
    }
}
