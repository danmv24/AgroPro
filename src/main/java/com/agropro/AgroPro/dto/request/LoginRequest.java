package com.agropro.AgroPro.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено")
    private String username;

     @NotBlank(message = "Пароль должен быть введён")
    private String password;

}
