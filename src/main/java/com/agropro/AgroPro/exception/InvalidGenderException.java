package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidGenderException extends InvalidEnumValueException {

    private final String gender;

    public InvalidGenderException(String gender) {
        super("Пол не найден: " + gender);
        this.gender = gender;
    }
}
