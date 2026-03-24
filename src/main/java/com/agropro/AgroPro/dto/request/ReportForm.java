package com.agropro.AgroPro.dto.request;

import com.agropro.AgroPro.enums.ReportType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportForm {

    @NotNull(message = "Тип отчёта должен быть указан")
    private ReportType reportType;

    @NotNull(message = "Дата начала должна быть указана")
    private LocalDate startDate;

    @NotNull(message = "Дата окончания должна быть указана")
    private LocalDate endDate;

}
