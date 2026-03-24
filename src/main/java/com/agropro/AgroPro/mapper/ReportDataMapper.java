package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.*;
import com.agropro.AgroPro.enums.EquipmentType;
import com.agropro.AgroPro.enums.MachineryType;
import com.agropro.AgroPro.projection.*;

import java.util.List;
import java.util.Map;

public class ReportDataMapper {

    public static ApkSeventeenReportData toSeventeenReportData(Map<MachineryType, TypeYearStat> machineryTypeStat,
                                                               Map<EquipmentType, TypeYearStat> equipmentTypeStat,
                                                               List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod,
                                                               List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod) {
        return ApkSeventeenReportData.builder()
                .machineryTypeStat(machineryTypeStat)
                .equipmentTypeStat(equipmentTypeStat)
                .expenseCategoryTotalAmountsForCurrentPeriod(expenseCategoryTotalAmountsForCurrentPeriod)
                .expenseCategoryTotalAmountsForPreviousPeriod(expenseCategoryTotalAmountsForPreviousPeriod)
                .build();
    }

    public static ApkFiveReportData toFiveReportData(List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmounts,
                                                     List<EmployeePositionCount> positionCounts, long totalEmployees,
                                                     long femaleEmployees, double totalWorkingHours,
                                                     List<WorkTypeHours> workTypeHours) {
        return ApkFiveReportData.builder()
                .expenseCategoryTotalAmounts(expenseCategoryTotalAmounts)
                .positionCounts(positionCounts)
                .totalEmployees(totalEmployees)
                .femaleEmployees(femaleEmployees)
                .totalWorkingHours(totalWorkingHours)
                .workTypeHours(workTypeHours)
                .build();
    }

    public static ApkEightReportData toEightReportData(List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod,
                                                       List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod) {
        return ApkEightReportData.builder()
                .expenseCategoryTotalAmountsForCurrentPeriod(expenseCategoryTotalAmountsForCurrentPeriod)
                .expenseCategoryTotalAmountsForPreviousPeriod(expenseCategoryTotalAmountsForPreviousPeriod)
                .build();
    }

    public static ApkNineReportData toNineReportData(List<CropArea> cropAreas, List<CropStatistic> cropStatistics) {
        return ApkNineReportData.builder()
                .cropAreas(cropAreas)
                .cropStatistics(cropStatistics)
                .build();
    }

}
