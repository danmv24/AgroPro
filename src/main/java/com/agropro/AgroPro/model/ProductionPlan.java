package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
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
@EqualsAndHashCode
public class ProductionPlan {

    @Id
    @Column("id")
    private Long id;

    // площади
    @Column("winter_wheat_area")
    private BigDecimal winterWheatArea;

    @Column("spring_wheat_area")
    private BigDecimal springWheatArea;

    @Column("spring_barley_area")
    private BigDecimal springBarleyArea;

    @Column("sunflower_area")
    private BigDecimal sunflowerArea;

    @Column("apple_area")
    private BigDecimal appleArea;

    // реализация
    @Column("winter_wheat_quantity_sale")
    private BigDecimal winterWheatQuantitySale;

    @Column("spring_wheat_quantity_sale")
    private BigDecimal springWheatQuantitySale;

    @Column("spring_barley_quantity_sale")
    private BigDecimal springBarleyQuantitySale;

    @Column("sunflower_quantity_sale")
    private BigDecimal sunflowerQuantitySale;

    @Column("apple_quantity_sale")
    private BigDecimal appleQuantitySale;

    // финансы
    @Column("total_cost")
    private BigDecimal totalCost;

    @Column("total_revenue")
    private BigDecimal totalRevenue;

    @Column("max_profit")
    private BigDecimal maxProfit;

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    @Column("created_at")
    private LocalDateTime createdAt;

}
