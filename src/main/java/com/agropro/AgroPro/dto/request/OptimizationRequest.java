package com.agropro.AgroPro.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OptimizationRequest {

    @NotNull(message = "Дата начала периода должна быть указана")
    private LocalDate startDate;

    @NotNull(message = "Дата окончания периода должна быть указана")
    private LocalDate endDate;

}
