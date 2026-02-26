package com.agropro.AgroPro.form;

import com.agropro.AgroPro.enums.EquipmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentForm {

    @NotBlank(message = "Название оборудования должно быть указано")
    private String equipmentName;

    @NotNull(message = "Тип оборудования должен быть указан")
    private EquipmentType equipmentType;

    @Positive
    @NotNull(message = "Инвентарный номер должен быть указан")
    private Integer inventoryNumber;

    @NotNull
    private LocalDate purchaseDate;

}
