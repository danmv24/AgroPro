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
import java.util.Locale;
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
        equipmentTypeStat.forEach(((equipmentType, stat) ->
                context.putVar(equipmentType.name().toLowerCase(Locale.ROOT), stat)));
    }

    private void fillMachineryData(Context context, Map<MachineryType, TypeYearStat> machineryTypeStat) {
        machineryTypeStat.forEach(((machineryType, stat) ->
                context.putVar(machineryType.name().toLowerCase(Locale.ROOT), stat)));
    }


}
