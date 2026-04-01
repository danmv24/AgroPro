package com.agropro.AgroPro.aggregator.impl;

import com.agropro.AgroPro.aggregator.DataAggregator;
import com.agropro.AgroPro.dto.internal.ApkFiveReportData;
import com.agropro.AgroPro.enums.Gender;
import com.agropro.AgroPro.mapper.ReportDataMapper;
import com.agropro.AgroPro.projection.EmployeePositionCount;
import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import com.agropro.AgroPro.projection.WorkTypeHours;
import com.agropro.AgroPro.repository.EmployeeRepository;
import com.agropro.AgroPro.repository.ExpenseCategoryRepository;
import com.agropro.AgroPro.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApkFiveReportAggregator implements DataAggregator<ApkFiveReportData> {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    private final EmployeeRepository employeeRepository;

    private final WorkRepository workRepository;

    private static final List<String> EXPENSE_CATEGORY_CODES = List.of(
            "52410",
            "52420",
            "52430",
            "52490",
            "52600"
    );

    @Override
    public ApkFiveReportData collectData(LocalDate startDate, LocalDate endDate) {
        List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmounts = collectExpenseData(startDate, endDate);
        List<EmployeePositionCount> positionCounts = collectEmployeePositionCount(startDate, endDate);
        long totalEmployees = collectTotalEmployees();
        long femaleEmployees = collectFemaleEmployees();
        Double totalHours = collectTotalWorkingHours(startDate, endDate);
        List<WorkTypeHours> workTypeHours = collectWorkTypeStat(startDate, endDate);

        return ReportDataMapper.toFiveReportData(expenseCategoryTotalAmounts, positionCounts, totalEmployees,
                femaleEmployees, totalHours, workTypeHours);
    }

    private List<WorkTypeHours> collectWorkTypeStat(LocalDate startDate, LocalDate endDate) {
        return workRepository.findWorkTypeWithTotalHours(startDate, endDate);
    }

    private long collectFemaleEmployees() {
        return employeeRepository.countEmployeesByGender(Gender.FEMALE);
    }

    private long collectTotalEmployees() {
        return employeeRepository.count();
    }

    private List<ExpenseCategoryTotalAmount> collectExpenseData(LocalDate startDate, LocalDate endDate) {
        return expenseCategoryRepository.findTotalAmountByCategoryCodesAndDateRange(EXPENSE_CATEGORY_CODES, startDate, endDate);
    }

    private List<EmployeePositionCount> collectEmployeePositionCount(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findEmployeeCountByPosition(startDate, endDate);
    }

    private Double collectTotalWorkingHours(LocalDate startDate, LocalDate endDate) {
        return workRepository.sumWorkingHoursBetweenStartDateAndEndDate(startDate, endDate);
    }


}
