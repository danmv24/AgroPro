package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class GenderNotFoundException extends NotFoundException {

    private final String gender;

    public GenderNotFoundException(String gender) {
        super("Пол не найден: " + gender);
        this.gender = gender;
    }
}
