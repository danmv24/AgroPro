package com.agropro.AgroPro.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FertilizerCategory {

    MINERAL("Минеральные"),
    MICRO("Микроудобрения"),
    SOIL_AMENDMENT("Удобрения для почвы");

    private final String fertilizerCategory;

}
