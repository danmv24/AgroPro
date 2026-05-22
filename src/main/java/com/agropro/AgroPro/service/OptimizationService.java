package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.OptimizationRequest;
import com.agropro.AgroPro.dto.response.ProductionPlanBasicResponse;
import com.agropro.AgroPro.dto.response.ProductionPlanResponse;
import org.springframework.data.domain.Slice;

public interface OptimizationService {

    ProductionPlanResponse createOptimization(OptimizationRequest optimizationRequest);

    Slice<ProductionPlanBasicResponse> getOptimizations(int page, int size);

    ProductionPlanResponse getOptimizationById(Long id);
}
