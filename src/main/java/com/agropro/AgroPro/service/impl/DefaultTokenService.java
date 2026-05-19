package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.security.CustomUserDetails;
import com.agropro.AgroPro.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultTokenService implements TokenService {

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    @Override
    public String generateAccessToken(CustomUserDetails userDetails) {
        Instant now = Instant.now();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new JwtException("У пользователя нет роли"));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("agropro")
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .subject(userDetails.getUsername())
                .claim("role", role)
                .claim("token_type", "access")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String generateRefreshToken(CustomUserDetails userDetails) {
        Instant now = Instant.now();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new JwtException("У пользователя нет роли"));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("agropro")
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(userDetails.getUsername())
                .claim("role", role)
                .claim("token_type", "refresh")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String parseRefreshToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        String tokenType = jwt.getClaim("token_type");

        if (!tokenType.equals("refresh")) {
            throw new JwtException("Неправильный тип токена");
        }

        return jwt.getSubject();
    }


}
