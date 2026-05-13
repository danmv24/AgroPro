package com.agropro.AgroPro.dto.request;

import com.agropro.AgroPro.enums.FertilizerType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkResultRequestOld {

    @Positive
    @NotNull
    private BigDecimal fuelUsed;

    @Positive
    private BigDecimal seedsUsed;

    @Positive
    private BigDecimal grossHarvest;

    private FertilizerType fertilizerType;

    @Positive
    private BigDecimal fertilizersUsed;

}
