package com.agropro.AgroPro.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkResultRequest {

    @NotEmpty(message = "Список материалов не должен быть пустым")
    private List<MaterialItem> materialItems;

    @Positive
    private BigDecimal grossHarvest;

}
