package com.agropro.AgroPro.aggregator.impl;

import com.agropro.AgroPro.aggregator.DataAggregator;
import com.agropro.AgroPro.dto.internal.ApkEightReportData;
import com.agropro.AgroPro.mapper.ReportDataMapper;
import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import com.agropro.AgroPro.repository.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApkEightReportAggregator implements DataAggregator<ApkEightReportData> {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    private static final List<String> EXPENSE_CATEGORY_CODES = List.of(
            "81111",
            "81112",
            "81131",
            "81140",
            "81151",
            "81161",
            "81171",
            "81172",
            "81180",
            "81192",
            "81240",
            "81250",
            "81260",
            "81290",
            "81400",
            "81610",
            "81620",
            "81690"
    );

    @Override
    public ApkEightReportData collectData(LocalDate startDate, LocalDate endDate) {
        List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod = collectExpenseData(startDate, endDate);
        List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod = collectExpenseData(
                startDate.minusYears(1), endDate.minusYears(1));

        return ReportDataMapper.toEightReportData(expenseCategoryTotalAmountsForCurrentPeriod,
                expenseCategoryTotalAmountsForPreviousPeriod);
    }

    private List<ExpenseCategoryTotalAmount> collectExpenseData(LocalDate startDate, LocalDate endDate) {
        return expenseCategoryRepository.findTotalAmountByCategoryCodesAndDateRange(EXPENSE_CATEGORY_CODES, startDate, endDate);
    }

}
