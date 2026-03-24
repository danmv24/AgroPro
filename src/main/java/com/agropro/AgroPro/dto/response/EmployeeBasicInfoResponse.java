package com.agropro.AgroPro.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class EmployeeBasicInfoResponse {

    private final Long employeeId;

    private final String surname;

    private final String name;

    private final String patronymic;

}
