package com.hemanth.redditclone.service;

import com.hemanth.redditclone.dto.NotificationEmail;
import com.hemanth.redditclone.dto.RegisterRequest;
import com.hemanth.redditclone.exceptions.SpringRedditException;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.model.VerificationToken;
import com.hemanth.redditclone.repository.UserRepository;
import com.hemanth.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUserName(registerRequest.getUserName());
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
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        Long userId = verificationToken.getUser().getUserId();
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new SpringRedditException("User not found with id - " + userId));
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
}
