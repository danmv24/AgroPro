package com.agropro.AgroPro.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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

    @Size(max = 300, message = "Описание работы не должно превышать 300 символов")
    private String description;

    @NotNull(message = "Должна быть выбрана дата начала работы")
    private LocalDateTime startDate;

    @NotNull(message = "Должна быть выбрана дата окончания работы")
    private LocalDateTime endDate;

    @Size(min = 1, message = "Должен быть выбран сотрудник")
    private Set<Long> employeeIds;

    private Set<Long> machineryIds;

    private Set<Long> equipmentIds;

}
