package com.agropro.AgroPro.generator.impl;

import com.agropro.AgroPro.aggregator.impl.ApkEightReportAggregator;
import com.agropro.AgroPro.dto.internal.ApkEightReportData;
import com.agropro.AgroPro.dto.request.ReportForm;
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

@Component
@RequiredArgsConstructor
public class ApkEightReportGenerator implements ReportGenerator {

    private final ApkEightReportAggregator aggregator;

    @Override
    public ReportType getSupportedType() {
        return ReportType.APK_8;
    }

    @Override
    public byte[] generate(ReportForm form) {
        ApkEightReportData data = aggregator.collectData(form.getStartDate(), form.getEndDate());

        try (InputStream reportTemplate = new ClassPathResource("reports/apk_8.xlsx").getInputStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Context context = new Context();

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

    private void fillExpenseData(Context context, List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForCurrentPeriod,
                                 List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmountsForPreviousPeriod) {
        for (ExpenseCategoryTotalAmount expenseCategoryStat : expenseCategoryTotalAmountsForCurrentPeriod) {
            context.putVar("expense_current_period_" + expenseCategoryStat.getCode(), expenseCategoryStat);
        }

        for (ExpenseCategoryTotalAmount expenseCategoryStat : expenseCategoryTotalAmountsForPreviousPeriod) {
            context.putVar("expense_previous_period_" + expenseCategoryStat.getCode(), expenseCategoryStat);
        }
    }


}
