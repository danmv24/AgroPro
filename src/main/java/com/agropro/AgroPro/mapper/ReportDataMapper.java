package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.internal.*;
import com.agropro.AgroPro.enums.EquipmentType;
import com.agropro.AgroPro.enums.MachineryType;
import com.agropro.AgroPro.projection.*;

import java.util.List;
import java.util.Map;

public class ReportDataMapper {

    private ReportDataMapper() {
    }

    public static ApkSeventeenReportInternalData toSeventeenReportInternalData(Map<MachineryType, TypeYearStatInternalData> machineryTypeStat,
                                                                               Map<EquipmentType, TypeYearStatInternalData> equipmentTypeStat,
                                                                               List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod,
                                                                               List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod) {
        return ApkSeventeenReportInternalData.builder()
                .machineryTypeStat(machineryTypeStat)
                .equipmentTypeStat(equipmentTypeStat)
                .expenseCategoryTotalAmountsForCurrentPeriod(expenseCategoryTotalAmountsForCurrentPeriod)
                .expenseCategoryTotalAmountsForPreviousPeriod(expenseCategoryTotalAmountsForPreviousPeriod)
                .build();
    }

    public static ApkFiveReportInternalData toFiveReportInternalData(List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmounts,
                                                                     List<EmployeePositionStatistic> positionCounts, long totalEmployees,
                                                                     long femaleEmployees, double totalWorkingHours,
                                                                     List<WorkTypeHours> workTypeHours) {
        return ApkFiveReportInternalData.builder()
                .expenseCategoryTotalAmounts(expenseCategoryTotalAmounts)
                .positionCounts(positionCounts)
                .totalEmployees(totalEmployees)
                .femaleEmployees(femaleEmployees)
                .totalWorkingHours(totalWorkingHours)
                .workTypeHours(workTypeHours)
                .build();
    }

    public static ApkEightReportInternalData toEightReportInternalData(List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod,
                                                                       List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod) {
        return ApkEightReportInternalData.builder()
                .expenseCategoryTotalAmountsForCurrentPeriod(expenseCategoryTotalAmountsForCurrentPeriod)
                .expenseCategoryTotalAmountsForPreviousPeriod(expenseCategoryTotalAmountsForPreviousPeriod)
                .build();
    }

    public static ApkNineReportInternalData toNineReportInternalData(List<CropArea> cropAreas, List<CropHarvest> cropHarvests,
                                                                     List<CropMaterialCost> cropMaterialCosts, List<CropLaborCost> cropLaborCosts) {
        return ApkNineReportInternalData.builder()
                .cropAreas(cropAreas)
                .cropHarvests(cropHarvests)
                .cropMaterialCosts(cropMaterialCosts)
                .cropLaborCosts(cropLaborCosts)
                .build();
    }

}
