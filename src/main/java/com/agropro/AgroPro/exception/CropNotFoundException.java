package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class CropNotFoundException extends RuntimeException {

    private final Long cropId;

    public CropNotFoundException(Long cropId) {
        super("Культура с id " + cropId + " не найдена");
        this.cropId = cropId;
    }
}
