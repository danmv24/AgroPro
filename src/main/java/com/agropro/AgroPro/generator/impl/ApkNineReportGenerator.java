package com.agropro.AgroPro.generator.impl;

import com.agropro.AgroPro.aggregator.impl.ApkNineReportAggregator;
import com.agropro.AgroPro.dto.internal.ApkNineReportData;
import com.agropro.AgroPro.dto.request.ReportRequest;
import com.agropro.AgroPro.enums.ReportType;
import com.agropro.AgroPro.generator.ReportGenerator;
import com.agropro.AgroPro.projection.CropArea;
import com.agropro.AgroPro.projection.CropHarvest;
import com.agropro.AgroPro.projection.CropLaborCost;
import com.agropro.AgroPro.projection.CropMaterialCost;
import lombok.RequiredArgsConstructor;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
            fillCropHarvestsData(context, data.getCropHarvests());
            fillCropMaterialCostData(context, data.getCropMaterialCosts());
            fillCropLaborCostData(context, data.getCropLaborCosts());

            JxlsHelper.getInstance()
                    .setEvaluateFormulas(true)
                    .setFullFormulaRecalculationOnOpening(true)
                    .processTemplate(reportTemplate, outputStream, context);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillCropLaborCostData(Context context, List<CropLaborCost> cropLaborCosts) {
        for (CropLaborCost cropLaborCost : cropLaborCosts) {
            context.putVar(cropLaborCost.getCropType().name().toLowerCase(), cropLaborCost);
        }
    }

    private void fillCropMaterialCostData(Context context, List<CropMaterialCost> cropMaterialCosts) {
        Map<String, Map<String, BigDecimal>> data = new HashMap<>();

        for (CropMaterialCost cropMaterialCost : cropMaterialCosts) {
            String cropName = cropMaterialCost.getCropType().name().toLowerCase(Locale.ROOT);
            String materialName = cropMaterialCost.getMaterialType().name().toLowerCase(Locale.ROOT);

            data.computeIfAbsent(cropName, k -> new HashMap<>())
                    .put(materialName, cropMaterialCost.getTotalCost());
        }

        context.putVar("crop_material_costs", data);
    }

    private void fillCropHarvestsData(Context context, List<CropHarvest> cropHarvests) {
        for (CropHarvest cropHarvest : cropHarvests) {
            context.putVar(cropHarvest.getCropType().name().toLowerCase(Locale.ROOT), cropHarvest);
        }
    }

    private void fillAreaData(Context context, List<CropArea> cropAreas) {
        for (CropArea cropArea : cropAreas) {
            context.putVar(cropArea.getCropType().name().toLowerCase(Locale.ROOT) + "_area", cropArea);
        }
    }

}
