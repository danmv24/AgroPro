package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.dto.request.WorkResultRequest;
import com.agropro.AgroPro.model.WorkResult;

public class WorkResultMapper {

    public static WorkResult toModel(Long workId, WorkResultRequest workResultRequest) {
        return WorkResult.builder()
                .workId(workId)
                .fuelUsed(workResultRequest.getFuelUsed())
                .seedsUsed(workResultRequest.getSeedsUsed())
                .yield(workResultRequest.getYield())
                .fertilizerType(workResultRequest.getFertilizerType())
                .fertilizersUsed(workResultRequest.getFertilizersUsed())
                .build();
    }

}