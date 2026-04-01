package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.OptimizationRequest;
import com.agropro.AgroPro.dto.response.OptimizationResponse;
import com.agropro.AgroPro.service.OptimizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/optimization")
public class OptimizationController {

    private final OptimizationService optimizationService;

    @PostMapping
    public OptimizationResponse createOptimization(@Valid @RequestBody OptimizationRequest optimizationRequest) {
        return optimizationService.createOptimization(optimizationRequest);
    }


}
