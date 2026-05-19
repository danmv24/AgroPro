package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class JwtResponse {

    private final String accessToken;

    private final long expiresIn;

}
