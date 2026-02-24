package com.agropro.AgroPro.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseForm {

    @NotNull(message = "Категория затрат должна быть выбрана")
    private Long categoryId;

    @NotNull(message = "Сумма затрат должна быть указана")
    private BigDecimal amount;

    @NotNull(message = "Дата должна быть указана")
    private LocalDate expenseDate;

    @Size(max = 255, message = "Описание не должно превышать 255 символом")
    private String description;

}
