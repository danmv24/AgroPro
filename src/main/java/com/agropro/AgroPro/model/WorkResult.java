package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.FertilizerType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("work_results")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkResult {

    @Id
    private Long id;

    @Column("work_id")
    private Long workId;

    @Column("fuel_used")
    private BigDecimal fuelUsed;

    @Column("seeds_used")
    private BigDecimal seedsUsed;

    @Column("yield")
    private BigDecimal yield;

    @Column("fertilizer_type")
    private FertilizerType fertilizerType;

    @Column("fertilizers_used")
    private BigDecimal fertilizersUsed;

}
