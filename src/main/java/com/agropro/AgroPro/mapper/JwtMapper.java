package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.response.JwtResponse;

public class JwtMapper {

    private JwtMapper() {
    }

    public static JwtResponse toResponse(String accessToken, long expiresIn) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .expiresIn(expiresIn)
                .build();
    }

}
