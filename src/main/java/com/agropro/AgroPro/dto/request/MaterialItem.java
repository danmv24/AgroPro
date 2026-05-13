package com.agropro.AgroPro.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MaterialItem {

    @NotNull
    private Long id;

    @Positive
    @NotNull
    private BigDecimal quantity;

}
