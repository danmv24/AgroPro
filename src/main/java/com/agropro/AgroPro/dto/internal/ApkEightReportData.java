package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
public class ApkEightReportData {

    private final List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod;

    private final List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod;

}
