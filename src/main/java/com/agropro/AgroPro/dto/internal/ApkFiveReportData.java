package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.projection.EmployeePositionCount;
import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import com.agropro.AgroPro.projection.WorkTypeHours;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class ApkFiveReportData {

    private final List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmounts;

    private final List<EmployeePositionCount> positionCounts;

    private final long totalEmployees;

    private final long femaleEmployees;

    private final double totalWorkingHours;

    private final List<WorkTypeHours> workTypeHours;

}
