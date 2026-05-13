package com.agropro.AgroPro.dto.request;

import com.agropro.AgroPro.enums.MaterialType;
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
public class MaterialRequest {

    @NotBlank(message = "Название должно быть указано")
    private String materialName;

    @NotNull(message = "Тип должен быть указан")
    private MaterialType materialType;

    @Positive(message = "Цена не может быть отрицательной")
    @NotNull(message = "Цена должна быть указана")
    private BigDecimal currentPrice;

}
