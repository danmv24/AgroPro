package com.agropro.AgroPro.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String username) {
        super("Пользователь с именем '" + username + "' не найден");
    }
}
