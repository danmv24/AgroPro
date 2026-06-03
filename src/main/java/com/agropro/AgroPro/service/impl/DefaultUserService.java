package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.dto.internal.UserInternalData;
import com.agropro.AgroPro.exception.UserNotFoundException;
import com.agropro.AgroPro.mapper.UserMapper;
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
    public UserInternalData getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new UserNotFoundException(auth.getName()));

        return UserMapper.toInternalData(user);
    }

}
