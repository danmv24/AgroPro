package com.agropro.AgroPro.generator.impl;

import com.agropro.AgroPro.aggregator.impl.ApkFiveReportAggregator;
import com.agropro.AgroPro.dto.internal.ApkFiveReportData;
import com.agropro.AgroPro.dto.request.ReportForm;
import com.agropro.AgroPro.enums.ReportType;
import com.agropro.AgroPro.generator.ReportGenerator;
import com.agropro.AgroPro.projection.EmployeePositionCount;
import com.agropro.AgroPro.projection.ExpenseCategoryTotalAmount;
import com.agropro.AgroPro.projection.WorkTypeHours;
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
public class ApkFiveReportGenerator implements ReportGenerator {

    private final ApkFiveReportAggregator aggregator;

    @Override
    public ReportType getSupportedType() {
        return ReportType.APK_5;
    }

    @Override
    public byte[] generate(ReportForm form) {
        ApkFiveReportData data = aggregator.collectData(form.getStartDate(), form.getEndDate());

        try (InputStream reportTemplate = new ClassPathResource("reports/apk_5.xlsx").getInputStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Context context = new Context();

            fillExpenseData(context, data.getExpenseCategoryTotalAmounts());
            fillPositionCountData(context, data.getPositionCounts());
            fillEmployeesDate(context, data.getTotalEmployees(), data.getFemaleEmployees());
            fillWorkingHours(context, data.getTotalWorkingHours(), data.getWorkTypeHours());

            JxlsHelper.getInstance()
                    .setEvaluateFormulas(true)
                    .setFullFormulaRecalculationOnOpening(true)
                    .processTemplate(reportTemplate, outputStream, context);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillWorkingHours(Context context, double totalWorkingHours, List<WorkTypeHours> workTypeHours) {
        context.putVar("total_working_hours", totalWorkingHours);
        for (WorkTypeHours wth : workTypeHours) {
            context.putVar(wth.getWorkType().name().toLowerCase(), wth);
        }
    }

    private void fillEmployeesDate(Context context, long totalEmployees, long femaleEmployees) {
        context.putVar("total_employees", totalEmployees);
        context.putVar("female_employees", femaleEmployees);
    }

    private void fillPositionCountData(Context context, List<EmployeePositionCount> positionCounts) {
        for (EmployeePositionCount positionCount : positionCounts) {
            context.putVar(positionCount.getPosition().name().toLowerCase(), positionCount);
        }

    }

    private void fillExpenseData(Context context, List<ExpenseCategoryTotalAmount> expenseCategoryTotalAmounts) {
        for (ExpenseCategoryTotalAmount expenseCategoryStat : expenseCategoryTotalAmounts) {
            context.putVar("expense_" + expenseCategoryStat.getCode(), expenseCategoryStat);
        }

    }
}
