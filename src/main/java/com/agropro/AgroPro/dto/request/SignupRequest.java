package com.agropro.AgroPro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotBlank
    @Size(min = 5, message = "Имя пользователя не должно быть меньше 5 символов")
    private String username;

    @NotBlank
    @Size(min = 10, message = "Пароль должен содержать минимум 10 символов")
    private String password;

    @NotNull(message = "Сотрудник должен быть заполнен")
    private Long employeeId;

}
