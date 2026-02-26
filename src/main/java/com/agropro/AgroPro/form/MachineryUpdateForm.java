package com.agropro.AgroPro.form;

import com.agropro.AgroPro.enums.MachineryType;
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
public class MachineryUpdateForm {

    @NotBlank(message = "Название техники должно быть указано")
    private String machineryName;

    @NotNull(message = "Тип техники должен быть указан")
    private MachineryType machineryType;

    @Positive
    @NotNull(message = "Инвентарный номер должен быть указан")
    private Integer inventoryNumber;

    @NotBlank(message = "Государственный регистрационный знак транспортного средства должен быть указан")
    private String licensePlate;

    @NotNull(message = "Статус техники должен быть укзан")
    private StatusCode status;

    @NotNull(message = "Дата покупки должна быть указана")
    private LocalDate purchaseDate;

}
