package com.hemanth.redditclone.security;

import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(username);
        User user = optionalUser
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found", username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities()
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }
}
