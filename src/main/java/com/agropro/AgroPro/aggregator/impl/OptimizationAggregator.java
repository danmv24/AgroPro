package com.agropro.AgroPro.aggregator.impl;

import com.agropro.AgroPro.aggregator.DataAggregator;
import com.agropro.AgroPro.dto.internal.OptimizationData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OptimizationAggregator implements DataAggregator<OptimizationData> {

    

    @Override
    public OptimizationData collectData(LocalDate startDate, LocalDate endDate) {
        return null;
    }



}
