package com.agropro.AgroPro.generator.impl;

import com.agropro.AgroPro.aggregator.impl.ApkNineReportAggregator;
import com.agropro.AgroPro.dto.internal.ApkNineReportData;
import com.agropro.AgroPro.dto.request.ReportRequest;
import com.agropro.AgroPro.enums.ReportType;
import com.agropro.AgroPro.generator.ReportGenerator;
import com.agropro.AgroPro.projection.CropArea;
import com.agropro.AgroPro.projection.CropStatistic;
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
public class ApkNineReportGenerator implements ReportGenerator {

    private final ApkNineReportAggregator aggregator;

    @Override
    public ReportType getSupportedType() {
        return ReportType.APK_9;
    }

    @Override
    public byte[] generate(ReportRequest form) {
        ApkNineReportData data = aggregator.collectData(form.getStartDate(), form.getEndDate());

        try (InputStream reportTemplate = new ClassPathResource("reports/apk_9.xlsx").getInputStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Context context = new Context();

            fillAreaData(context, data.getCropAreas());
            fillCropStatisticData(context, data.getCropStatistics());

            JxlsHelper.getInstance()
                    .setEvaluateFormulas(true)
                    .setFullFormulaRecalculationOnOpening(true)
                    .processTemplate(reportTemplate, outputStream, context);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillCropStatisticData(Context context, List<CropStatistic> cropStatistics) {
        for (CropStatistic cropStatistic : cropStatistics) {
            context.putVar(cropStatistic.getCropType().name().toLowerCase(), cropStatistic);
        }
    }

    private void fillAreaData(Context context, List<CropArea> cropAreas) {
        for (CropArea cropArea : cropAreas) {
            context.putVar(cropArea.getCropType().name().toLowerCase() + "_area", cropArea);
        }
    }


}
