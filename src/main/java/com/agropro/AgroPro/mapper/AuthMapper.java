package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.AuthTokenInternalData;

public class AuthMapper {

    private AuthMapper() {
    }

    public static AuthTokenInternalData toInternalData(String accessToken, String refreshToken, long expiresIn) {
        return AuthTokenInternalData.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .build();
    }

}
