package com.agropro.AgroPro.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldWorkForm {

    @NotNull(message = "Тип работы должен быть указан")
    private Long workTypeId;

    @NotNull(message = "Поле должно быть выбрано")
    private Long fieldId;

    @NotNull(message = "Должна быть выбрана дата начала работы")
    private LocalDateTime startDate;

    @NotNull(message = "Должна быть выбрана дата окончания работы")
    private LocalDateTime endDate;

    @Size(min = 1, message = "Должен быть выбран сотрудник")
    private List<Long> employeeIds;

    private List<Long> machineryIds;

    private List<Long> equipmentIds;

}
