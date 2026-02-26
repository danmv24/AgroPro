package com.agropro.AgroPro.form;

import com.agropro.AgroPro.enums.EquipmentType;
import com.agropro.AgroPro.enums.StatusCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EquipmentUpdateForm {

    @NotBlank(message = "Название оборудования должно быть указано")
    private String equipmentName;

    @NotNull(message = "Тип оборудования должен быть указан")
    private EquipmentType equipmentType;

    @Positive
    @NotNull(message = "Инвентарный номер должен быть указан")
    private Integer inventoryNumber;

    @NotNull(message = "Дата покупки должна быть указана")
    private LocalDate purchaseDate;

    @NotBlank(message = "Статус оборудования должен быть указан")
    private StatusCode status;

}
