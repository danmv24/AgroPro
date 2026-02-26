package com.agropro.AgroPro.view;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
public class WorkView {

    private final Long id;

    private final WorkType workType;

    private final WorkStatus status;

    private final Integer fieldNumber;

    private final String description;

    private final List<EmployeeBasicInfoView> employees;

    private final List<MachineryBasicInfoView> machineries;

    private final List<EquipmentBasicInfoView> equipment;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

}
