package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Builder
public class WorkRecordView {

    private final String surname;

    private final String name;

    private final String patronymic;

    private final LocalDate workDate;

    private final Double hoursWorked;

    private final Double amountEarned;

}
