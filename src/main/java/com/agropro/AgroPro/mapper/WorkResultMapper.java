package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.WorkResultForm;
import com.agropro.AgroPro.model.WorkResult;

public class WorkResultMapper {

    public static WorkResult toModel(Long workId, WorkResultForm workResultForm) {
        return WorkResult.builder()
                .workId(workId)
                .fuelUsed(workResultForm.getFuelUsed())
                .seedsUsed(workResultForm.getSeedsUsed())
                .yield(workResultForm.getYield())
                .fertilizerType(workResultForm.getFertilizerType())
                .fertilizersUsed(workResultForm.getFertilizersUsed())
                .build();
    }

}