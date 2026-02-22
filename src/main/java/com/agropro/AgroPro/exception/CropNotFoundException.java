package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class CropNotFoundException extends NotFoundException {

    private final String cropType;

    public CropNotFoundException(String cropType) {
        super("Культура с названием '" + cropType + "' не найдена");
        this.cropType = cropType;
    }
}
