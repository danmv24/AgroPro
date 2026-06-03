package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.dto.internal.AuthTokenInternalData;
import com.agropro.AgroPro.dto.internal.EmployeeInternalData;
import com.agropro.AgroPro.dto.request.LoginRequest;
import com.agropro.AgroPro.dto.request.SignupRequest;
import com.agropro.AgroPro.enums.Role;
import com.agropro.AgroPro.exception.EmployeeAlreadyHasAccountException;
import com.agropro.AgroPro.exception.UserAlreadyExistsException;
import com.agropro.AgroPro.mapper.AuthMapper;
import com.agropro.AgroPro.mapper.RoleMapper;
import com.agropro.AgroPro.mapper.UserMapper;
import com.agropro.AgroPro.repository.UserRepository;
import com.agropro.AgroPro.security.CustomUserDetails;
import com.agropro.AgroPro.service.AuthService;
import com.agropro.AgroPro.service.EmployeeService;
import com.agropro.AgroPro.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final EmployeeService employeeService;

    @Override
    public AuthTokenInternalData authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = tokenService.generateAccessToken(userDetails);
        String refreshToken = tokenService.generateRefreshToken(userDetails);

        return AuthMapper.toInternalData(accessToken, refreshToken,900);
    }

    @Override
    public AuthTokenInternalData refresh(String refreshToken) {
        String username = tokenService.parseRefreshToken(refreshToken);

        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        String newAccessToken = tokenService.generateAccessToken(user);
        String newRefreshToken = tokenService.generateRefreshToken(user);

        return AuthMapper.toInternalData(newAccessToken, newRefreshToken, 900);
    }

    @Override
    public void logout() {

    }

    @Override
    public void createUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new UserAlreadyExistsException(signupRequest.getUsername());
        }

        if (userRepository.existsByEmployeeId(signupRequest.getEmployeeId())) {
            throw new EmployeeAlreadyHasAccountException();
        }

        EmployeeInternalData employee = employeeService.getEmployeeById(signupRequest.getEmployeeId());
        Role role = RoleMapper.fromPosition(employee.getPosition());
        String password = passwordEncoder.encode(signupRequest.getPassword());

        userRepository.save(UserMapper.toModel(signupRequest, password, role, employee.getId()));
    }


}
