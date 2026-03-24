package com.agropro.AgroPro.generator.impl;

import com.agropro.AgroPro.aggregator.impl.ApkSeventeenReportAggregator;
import com.agropro.AgroPro.dto.internal.ApkSeventeenReportData;
import com.agropro.AgroPro.dto.internal.TypeYearStat;
import com.agropro.AgroPro.dto.request.ReportRequest;
import com.agropro.AgroPro.enums.EquipmentType;
import com.agropro.AgroPro.enums.MachineryType;
import com.agropro.AgroPro.enums.ReportType;
import com.agropro.AgroPro.generator.ReportGenerator;
import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import lombok.RequiredArgsConstructor;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class ApkSeventeenReportGenerator implements ReportGenerator {

    private final ApkSeventeenReportAggregator aggregator;

    @Override
    public ReportType getSupportedType() {
        return ReportType.APK_17;
    }

    @Override
    public byte[] generate(ReportRequest form) {
        ApkSeventeenReportData data = aggregator.collectData(form.getStartDate(), form.getEndDate());

        try (InputStream reportTemplate = new ClassPathResource("reports/apk_17.xlsx").getInputStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Context context = new Context();

            fillMachineryData(context, data.getMachineryTypeStat());
            fillEquipmentData(context, data.getEquipmentTypeStat());
            fillExpenseData(context, data.getExpenseCategoryTotalAmountsForCurrentPeriod(),
                    data.getExpenseCategoryTotalAmountsForPreviousPeriod());

            JxlsHelper.getInstance()
                    .setEvaluateFormulas(true)
                    .setFullFormulaRecalculationOnOpening(true)
                    .processTemplate(reportTemplate, outputStream, context);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillExpenseData(Context context, List<ExpenseCategoryTotalAmount> expenseCategoryStatsCurrent,
                                 List<ExpenseCategoryTotalAmount> expenseCategoryStatsPrevious) {
        for (ExpenseCategoryTotalAmount expenseCategoryStat : expenseCategoryStatsCurrent) {
            context.putVar("expense_current_period_" + expenseCategoryStat.getCode(), expenseCategoryStat);
        }

        for (ExpenseCategoryTotalAmount expenseCategoryStat : expenseCategoryStatsPrevious) {
            context.putVar("expense_previous_period_" + expenseCategoryStat.getCode(), expenseCategoryStat);
        }
    }

    private void fillEquipmentData(Context context, Map<EquipmentType, TypeYearStat> equipmentTypeStat) {
        context.putVar("plow", equipmentTypeStat.get(EquipmentType.PLOW));
        context.putVar("seeder", equipmentTypeStat.get(EquipmentType.SEEDER));
        context.putVar("cultivator", equipmentTypeStat.get(EquipmentType.CULTIVATOR));
        context.putVar("diskator", equipmentTypeStat.get(EquipmentType.DISKATOR));
        context.putVar("trailer", equipmentTypeStat.get(EquipmentType.TRAILER));
        context.putVar("mower", equipmentTypeStat.get(EquipmentType.MOWER));
        context.putVar("fertilizerDispenser", equipmentTypeStat.get(EquipmentType.FERTILIZER_DISPENSER));
        context.putVar("sprayer", equipmentTypeStat.get(EquipmentType.SPRAYER));
        context.putVar("harrow", equipmentTypeStat.get(EquipmentType.HARROW));
        context.putVar("rollingRinks", equipmentTypeStat.get(EquipmentType.ROLLING_RINKS));
        context.putVar("irrigationSystem", equipmentTypeStat.get(EquipmentType.IRRIGATION_SYSTEM));
    }

    private void fillMachineryData(Context context, Map<MachineryType, TypeYearStat> machineryTypeStat) {
        context.putVar("tractor", machineryTypeStat.get(MachineryType.TRACTOR));
        context.putVar("combine", machineryTypeStat.get(MachineryType.COMBINE_HARVESTER));
        context.putVar("truck", machineryTypeStat.get(MachineryType.TRUCK));
        context.putVar("transport", machineryTypeStat.get(MachineryType.TRANSPORT_VEHICLE));
        context.putVar("forklift", machineryTypeStat.get(MachineryType.FORKLIFT));
        context.putVar("hayHarvester", machineryTypeStat.get(MachineryType.HAY_HARVESTER));
    }


}
