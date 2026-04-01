package com.agropro.AgroPro.aggregator.impl;

import com.agropro.AgroPro.aggregator.DataAggregator;
import com.agropro.AgroPro.dto.internal.ApkSeventeenReportData;
import com.agropro.AgroPro.dto.internal.TypeYearStat;
import com.agropro.AgroPro.enums.EquipmentType;
import com.agropro.AgroPro.enums.MachineryType;
import com.agropro.AgroPro.mapper.ReportDataMapper;
import com.agropro.AgroPro.projection.EquipmentTypeCount;
import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import com.agropro.AgroPro.projection.MachineryTypeCount;
import com.agropro.AgroPro.repository.EquipmentRepository;
import com.agropro.AgroPro.repository.ExpenseCategoryRepository;
import com.agropro.AgroPro.repository.MachineryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApkSeventeenReportAggregator implements DataAggregator<ApkSeventeenReportData> {

    private final MachineryRepository machineryRepository;

    private final EquipmentRepository equipmentRepository;

    private final ExpenseCategoryRepository expenseCategoryRepository;

    private static final List<String> EXPENSE_CATEGORY_CODES = List.of(
            "172130",
            "172141",
            "172151",
            "172161"
    );

    @Override
    public ApkSeventeenReportData collectData(LocalDate startDate, LocalDate endDate) {
        Map<MachineryType, TypeYearStat> machineryTypeStat = collectMachineryData(startDate, endDate);
        Map<EquipmentType, TypeYearStat> equipmentTypeStat = collectEquipmentData(startDate, endDate);
        List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod = collectExpenseData(startDate, endDate);
        List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod = collectExpenseData(
                startDate.minusYears(1), endDate.minusYears(1));

        return ReportDataMapper.toSeventeenReportData(machineryTypeStat, equipmentTypeStat, expenseCategoryTotalAmountsForCurrentPeriod,
                expenseCategoryTotalAmountsForPreviousPeriod);
    }

    private List<ExpenseCategoryTotalAmount> collectExpenseData(LocalDate startDate, LocalDate endDate) {
        return expenseCategoryRepository.findTotalAmountByCategoryCodesAndDateRange(EXPENSE_CATEGORY_CODES, startDate, endDate);
    }

    private Map<EquipmentType, TypeYearStat> collectEquipmentData(LocalDate startDate, LocalDate endDate) {
        List<EquipmentTypeCount> equipmentByTypeAtStartDate = equipmentRepository.countEquipmentByEquipmentTypeAtStartDate(startDate);
        List<EquipmentTypeCount> incomingEquipmentByType = equipmentRepository.countEquipmentByEquipmentTypeAndPurchaseDateBetween(startDate, endDate);
        List<EquipmentTypeCount> decommissionedEquipmentByType = equipmentRepository.countDecommissionedEquipmentByEquipmentTypeAndBetweenDate(startDate, endDate);
        List<EquipmentTypeCount> equipmentByTypeAtEndDate = equipmentRepository.countEquipmentByEquipmentTypeAtEndDate(endDate);

        Map<EquipmentType, TypeYearStat> equipmentTypeStat = new EnumMap<>(EquipmentType.class);

        for (EquipmentType equipmentType : EquipmentType.values()) {
            equipmentTypeStat.put(equipmentType, new TypeYearStat());
        }

        equipmentByTypeAtStartDate.forEach(row ->
                equipmentTypeStat.get(row.getEquipmentType()).setAtStartDateCount(row.getCount()));

        incomingEquipmentByType.forEach(row ->
                equipmentTypeStat.get(row.getEquipmentType()).setIncomingCount(row.getCount()));

        decommissionedEquipmentByType.forEach(row ->
                equipmentTypeStat.get(row.getEquipmentType()).setDecommissionedCount(row.getCount()));

        equipmentByTypeAtEndDate.forEach(row ->
                equipmentTypeStat.get(row.getEquipmentType()).setAtEndDateCount(row.getCount()));

        return equipmentTypeStat;
    }

    private Map<MachineryType, TypeYearStat> collectMachineryData(LocalDate startDate, LocalDate endDate) {
        List<MachineryTypeCount> machineryByTypeAtStartDate = machineryRepository.countMachineryByMachineryTypeAtStartDate(startDate);
        List<MachineryTypeCount> incomingMachineryByType = machineryRepository.countMachineryByMachineryTypeAndPurchaseDateBetween(startDate, endDate);
        List<MachineryTypeCount> decommissionedMachineryByType = machineryRepository.countDecommissionedMachineryByMachineryTypeAndBetweenDate(startDate, endDate);
        List<MachineryTypeCount> machineryByTypeAtEndDate = machineryRepository.countMachineryByMachineryTypeAtEndDate(endDate);

        Map<MachineryType, TypeYearStat> machineryTypeStat = new EnumMap<>(MachineryType.class);

        for (MachineryType machineryType : MachineryType.values()) {
            machineryTypeStat.put(machineryType, new TypeYearStat());
        }

        machineryByTypeAtStartDate.forEach(row ->
                machineryTypeStat.get(row.getMachineryType()).setAtStartDateCount(row.getCount()));

        incomingMachineryByType.forEach(row ->
                machineryTypeStat.get(row.getMachineryType()).setIncomingCount(row.getCount()));

        decommissionedMachineryByType.forEach(row ->
                machineryTypeStat.get(row.getMachineryType()).setDecommissionedCount(row.getCount()));

        machineryByTypeAtEndDate.forEach(row ->
                machineryTypeStat.get(row.getMachineryType()).setAtEndDateCount(row.getCount()));

        return machineryTypeStat;
    }


}
