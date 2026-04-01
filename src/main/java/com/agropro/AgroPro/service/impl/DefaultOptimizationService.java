package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.aggregator.impl.OptimizationAggregator;
import com.agropro.AgroPro.dto.internal.OptimizationData;
import com.agropro.AgroPro.dto.request.OptimizationRequest;
import com.agropro.AgroPro.dto.response.OptimizationResponse;
import com.agropro.AgroPro.service.OptimizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultOptimizationService implements OptimizationService {

    private final OptimizationAggregator aggregator;

    @Override
    public OptimizationResponse createOptimization(OptimizationRequest optimizationRequest) {
        OptimizationData data = aggregator.collectData(optimizationRequest.getStartDate(), optimizationRequest.getEndDate());



        return null;
    }
}
