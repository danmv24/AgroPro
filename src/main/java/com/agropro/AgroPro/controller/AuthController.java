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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        AuthToken tokens = authService.authenticate(loginRequest);

        addRefreshCookie(response, tokens.getRefreshToken());

        return JwtMapper.toResponse(tokens.getAccessToken(), tokens.getExpiresIn());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {
        try {
            AuthToken tokens = authService.refresh(refreshToken);
            addRefreshCookie(response, tokens.getRefreshToken());
            return ResponseEntity.ok(JwtMapper.toResponse(tokens.getAccessToken(), tokens.getExpiresIn()));
        } catch (JwtException e) {
            // Токен просрочен, подпись невалидна или неверный тип
            log.warn("Refresh token invalid: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "INVALID_TOKEN", "message", e.getMessage()));
        } catch (UsernameNotFoundException e) {
            // Пользователь удалён или отключён
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "USER_NOT_FOUND"));
        } catch (Exception e) {
            log.error("Unexpected refresh error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    public void signup(@Valid @RequestBody SignupRequest signupRequest) {
        authService.createUser(signupRequest);
    }

}
