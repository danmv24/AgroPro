package com.agropro.AgroPro.form;

import com.agropro.AgroPro.enums.WorkType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkUpdateForm {

    @NotNull(message = "Тип работы должен быть указан")
    private WorkType workType;

    @NotNull(message = "Поле должно быть выбрано")
    private Long fieldId;

    @Size(max = 300, message = "Описание работы не должно превышать 300 символов")
    private String description;

    @NotNull(message = "Должна быть выбрана дата начала работы")
    @FutureOrPresent
    private LocalDateTime startDate;

    @NotNull(message = "Должна быть выбрана дата окончания работы")
    @Future
    private LocalDateTime endDate;

    @Size(min = 1, message = "Должен быть выбран сотрудник")
    @NotEmpty
    private Set<Long> employeeIds;

    @Size(min = 1, message = "Должна быть выбрана техника")
    @NotEmpty
    private Set<Long> machineryIds;

    @NotNull
    private Set<Long> equipmentIds;

    private String cropName;

}
