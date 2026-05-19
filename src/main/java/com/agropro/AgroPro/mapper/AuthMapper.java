package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.AuthToken;

public class AuthMapper {

    public static AuthToken toAuthToken(String accessToken, String refreshToken, long expiresIn) {
        return AuthToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .build();
    }

}
