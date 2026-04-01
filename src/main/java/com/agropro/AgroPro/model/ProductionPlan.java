package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("production_plan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductionPlan {

    @Id
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    // площади
    private double winterWheatArea;
    private double springWheatArea;
    private double springBarleyArea;
    private double sunflowerArea;
    private double soyArea;
    private double appleArea;

    // объемы
    private double appleJuiceVolume;

    // реализация
    private double winterWheatSale;
    private double springWheatSale;
    private double springBarleySale;
    private double sunflowerSale;
    private double appleJuiceSale;
    private double soySale;
    private double appleSale;

    // финансы
    private BigDecimal totalCost;
    private BigDecimal totalRevenue;
    private BigDecimal maxProfit;

    private LocalDateTime createdAt;

}
