package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.UserNotFoundException;
import com.agropro.AgroPro.model.User;
import com.agropro.AgroPro.repository.UserRepository;
import com.agropro.AgroPro.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return new CustomUserDetails(user);
    }
}
