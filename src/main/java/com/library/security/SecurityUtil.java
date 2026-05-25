package com.library.security;

import com.library.entity.User;
import com.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user= userRepository.findByEmail(email)
        		.orElseThrow(()-> new RuntimeException());
        return user;
    }
}