package com.agropro.AgroPro.service;

import com.agropro.AgroPro.security.CustomUserDetails;

public interface TokenService {

    String generateAccessToken(CustomUserDetails userDetails);

    String generateRefreshToken(CustomUserDetails userDetails);

    String parseRefreshToken(String token);

}
