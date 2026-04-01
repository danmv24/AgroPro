package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.OptimizationRequest;
import com.agropro.AgroPro.dto.response.OptimizationResponse;

public interface OptimizationService {

    OptimizationResponse createOptimization(OptimizationRequest optimizationRequest);

}
