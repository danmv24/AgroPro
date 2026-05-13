package com.agropro.AgroPro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MaterialUpdateRequest {

    @NotBlank(message = "Название должно быть указано")
    private String materialName;

    @Positive(message = "Цена не может быть отрицательной")
    @NotNull(message = "Цена должна быть указана")
    private BigDecimal currentPrice;

}
