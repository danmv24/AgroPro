package com.agropro.AgroPro.view;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class EmployeeBasicInfo {

    private final Long employeeId;

    private final String surname;

    private final String name;

    private final String patronymic;

}
