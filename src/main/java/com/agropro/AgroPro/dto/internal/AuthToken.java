package com.agropro.AgroPro.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class AuthToken {

    private final String accessToken;

    private final String refreshToken;

    private final long expiresIn;

}
