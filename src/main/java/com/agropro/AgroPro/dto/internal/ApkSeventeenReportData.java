package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.enums.EquipmentType;
import com.agropro.AgroPro.enums.MachineryType;
import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@RequiredArgsConstructor
public class ApkSeventeenReportData {

    private final Map<MachineryType, TypeYearStat> machineryTypeStat;

    private final Map<EquipmentType, TypeYearStat> equipmentTypeStat;

    private final List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod;

    private final List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod;

}
