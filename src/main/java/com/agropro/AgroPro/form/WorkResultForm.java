package com.agropro.AgroPro.form;

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
public class WorkResultForm {

    @Positive
    @NotNull
    private BigDecimal fuelUsed;

    private BigDecimal seedsUsed;

    private BigDecimal harvestAmount;

    private FertilizerType fertilizerType;

    private BigDecimal fertilizerAmount;

    private BigDecimal waterAmount;

}
