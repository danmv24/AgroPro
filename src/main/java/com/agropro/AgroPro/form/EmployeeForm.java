package com.agropro.AgroPro.form;


import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.PaymentType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeeForm {

    @NotBlank(message = "Фамилия должна быть указана")
    @Size(min = 2, max = 60, message = "Длина фамилии должна быть от 2 до 60 символов")
    private String surname;

    @NotBlank(message = "Имя должно быть указано")
    @Size(min = 2, max = 30, message = "Длина имени должна быть от 2 до 30 символов")
    private String name;

    @NotBlank(message = "Отчество должно быть указано")
    @Size(min = 2, max = 70, message = "Длина отчества должна быть от 2 до 30 символов")
    private String patronymic;

    @NotNull(message = "Должность должна быть выбрана")
    private EmployeePosition position;

    @NotNull(message = "Тип оплаты должен быть выбран")
    private PaymentType paymentType;

    @NotNull(message = "Зарплата/ставка должна быть указана")
    @Positive(message = "Зарплата должна быть больше 0")
    @Digits(integer = 6, fraction = 2, message = "Зарплата должна содержать до 6 знаков целой части и до 2 знаков дробной")
    private BigDecimal salary;

    @NotNull(message = "Дата приёма на работу должна быть указана")
    private LocalDate hireDate;

    @NotBlank(message = "Пол сотрудника должен быть выбран")
    private String gender;
}
