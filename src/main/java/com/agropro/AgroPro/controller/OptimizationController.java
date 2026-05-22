package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.OptimizationRequest;
import com.agropro.AgroPro.dto.response.ProductionPlanBasicResponse;
import com.agropro.AgroPro.dto.response.ProductionPlanResponse;
import com.agropro.AgroPro.service.OptimizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/optimization")
public class OptimizationController {

    private final OptimizationService optimizationService;

    @PostMapping("/create")
    public ProductionPlanResponse createOptimization(@Valid @RequestBody OptimizationRequest optimizationRequest) {
        return optimizationService.createOptimization(optimizationRequest);
    }

    @GetMapping
    public Slice<ProductionPlanBasicResponse> getOptimizations(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "15") int size) {
        return optimizationService.getOptimizations(page, size);
    }

    @GetMapping("/{id}")
    public ProductionPlanResponse getOptimization(@PathVariable Long id) {
        return optimizationService.getOptimizationById(id);
    }

}
