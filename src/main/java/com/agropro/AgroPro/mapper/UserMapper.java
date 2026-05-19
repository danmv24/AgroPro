package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.SignupRequest;
import com.agropro.AgroPro.enums.Role;
import com.agropro.AgroPro.model.User;

public class UserMapper {

    public static User toModel(SignupRequest signupRequest, String password, Role role, Long employeeId) {
        return User.builder()
                .username(signupRequest.getUsername())
                .password(password)
                .role(role)
                .employeeId(employeeId)
                .build();
    }

}
