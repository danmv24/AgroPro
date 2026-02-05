package com.agropro.AgroPro.exception;

import lombok.Getter;

@Getter
public class PositionNotFoundException extends RuntimeException {


    public PositionNotFoundException(String message) {
        super(message);
    }

}
