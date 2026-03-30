package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class InvalidCropTypeException extends InvalidEnumValueException {

    private final String cropType;

    public InvalidCropTypeException(String cropType) {
        super("Культура с названием '" + cropType + "' не найдена");
        this.cropType = cropType;
    }
}
