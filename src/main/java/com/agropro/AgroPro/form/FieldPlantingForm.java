package com.agropro.AgroPro.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FieldPlantingForm {

    @NotNull(message = "Поле должно быть указано")
    private Long fieldId;

    @NotBlank(message = "Культура должна быть указана")
    private String cropType;

    @NotNull(message = "Дата посева должен быть указан")
    private LocalDate plantingDate;

}
