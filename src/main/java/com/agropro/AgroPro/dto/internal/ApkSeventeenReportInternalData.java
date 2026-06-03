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
public class ApkSeventeenReportInternalData {

    private final Map<MachineryType, TypeYearStatInternalData> machineryTypeStat;

    private final Map<EquipmentType, TypeYearStatInternalData> equipmentTypeStat;

    private final List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod;

    private final List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod;

}
