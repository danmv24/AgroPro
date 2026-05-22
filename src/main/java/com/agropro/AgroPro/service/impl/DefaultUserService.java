package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.UserNotFoundException;
import com.agropro.AgroPro.model.User;
import com.agropro.AgroPro.repository.UserRepository;
import com.agropro.AgroPro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return getByUsername(auth.getName());
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}
