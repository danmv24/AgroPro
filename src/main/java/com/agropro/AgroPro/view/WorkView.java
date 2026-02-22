package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
public class WorkView {

    private final Long fieldWorkId;

    private final String workType;

    private final String status;

    private final Integer fieldNumber;

    private final String description;

    private final List<EmployeeBasicInfoView> employees;

    private final List<MachineryBasicInfoView> machineries;

    private final List<EquipmentBasicInfoView> equipment;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

}
