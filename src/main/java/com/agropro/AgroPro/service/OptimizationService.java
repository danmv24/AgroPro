package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.OptimizationRequest;
import com.agropro.AgroPro.dto.response.ProductionPlanResponse;

public interface OptimizationService {

    ProductionPlanResponse createOptimization(OptimizationRequest optimizationRequest);

}
