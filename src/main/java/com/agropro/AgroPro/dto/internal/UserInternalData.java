package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class UserInternalData {

    private final Long id;

    private final String username;

    private final String password;

    private final Role role;

    private final Long employeeId;

}
