package com.agropro.AgroPro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CropNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CropNotFoundException(HttpStatus httpStatus, Long cropId) {
        super("Культура с id " + cropId + " не найдена");
        this.httpStatus = httpStatus;
    }
}
