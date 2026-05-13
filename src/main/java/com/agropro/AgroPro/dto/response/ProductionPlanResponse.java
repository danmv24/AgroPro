package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class ProductionPlanResponse {

    private final Long id;

    private final BigDecimal winterWheatArea;

    private final BigDecimal springWheatArea;

    private final BigDecimal springBarleyArea;

    private final BigDecimal sunflowerArea;

    private final BigDecimal appleArea;

    private final BigDecimal winterWheatQuantitySale;

    private final BigDecimal springWheatQuantitySale;

    private final BigDecimal springBarleyQuantitySale;

    private final BigDecimal sunflowerQuantitySale;

    private final BigDecimal appleQuantitySale;

    private final BigDecimal totalCost;

    private final BigDecimal totalRevenue;

    private final BigDecimal maxProfit;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final LocalDateTime createdAt;

}
