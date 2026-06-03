package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.internal.AuthTokenInternalData;
import com.agropro.AgroPro.dto.request.LoginRequest;
import com.agropro.AgroPro.dto.request.SignupRequest;

public interface AuthService {

    AuthTokenInternalData authenticate(LoginRequest userRequest);

    AuthTokenInternalData refresh(String refreshToken);

    void logout();

    void createUser(SignupRequest signupRequest);
}
