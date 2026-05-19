package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.internal.AuthToken;
import com.agropro.AgroPro.dto.request.LoginRequest;
import com.agropro.AgroPro.dto.request.SignupRequest;

public interface AuthService {

    AuthToken authenticate(LoginRequest userRequest);

    AuthToken refresh(String refreshToken);

    void logout();

    void createUser(SignupRequest signupRequest);
}
