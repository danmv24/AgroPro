package com.agropro.AgroPro.view;

import com.agropro.AgroPro.enums.FieldWorkStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldWorkView {

    private final Long fieldWorkId;

    private final String workTypeName;

    private final FieldWorkStatus status;

    private final Integer fieldNumber;

    private final String description;

    private final List<EmployeeBasicInfoView> employees;

    private final List<MachineryBasicInfoView> machineries;

    private final List<EquipmentBasicInfoView> equipment;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

}
