package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.internal.AuthToken;
import com.agropro.AgroPro.dto.request.LoginRequest;
import com.agropro.AgroPro.dto.request.SignupRequest;
import com.agropro.AgroPro.dto.response.JwtResponse;
import com.agropro.AgroPro.mapper.JwtMapper;
import com.agropro.AgroPro.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        AuthToken tokens = authService.authenticate(loginRequest);

        addRefreshCookie(response, tokens.getRefreshToken());

        return JwtMapper.toResponse(tokens.getAccessToken(), tokens.getExpiresIn());
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {
        AuthToken tokens = authService.refresh(refreshToken);

        addRefreshCookie(response, tokens.getRefreshToken());

        return JwtMapper.toResponse(tokens.getAccessToken(), tokens.getExpiresIn());
    }

    private void addRefreshCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/api/auth/refresh")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @PostMapping("signup")
    @PreAuthorize("hasRole('ACCOUNTANT')")
    public void signup(@Valid @RequestBody SignupRequest signupRequest) {
        authService.createUser(signupRequest);
    }

}
