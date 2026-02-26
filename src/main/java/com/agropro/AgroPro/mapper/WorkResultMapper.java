package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.form.WorkResultForm;
import com.agropro.AgroPro.model.WorkResult;

public class WorkResultMapper {

    public static WorkResult toModel(Long workId, WorkResultForm workResultForm) {
        return WorkResult.builder()
                .workId(workId)
                .fuelUsed(workResultForm.getFuelUsed())
                .seedsUsed(workResultForm.getSeedsUsed())
                .harvestAmount(workResultForm.getHarvestAmount())
                .fertilizerType(workResultForm.getFertilizerType())
                .fertilizerAmount(workResultForm.getFertilizerAmount())
                .waterAmount(workResultForm.getWaterAmount())
                .build();
    }

}