package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.InvalidProductException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Product {

    WINTER_WHEAT("Пшеница озимая"),
    SPRING_WHEAT("Пшеница яровая"),
    SPRING_BARLEY("Ячмень яровой"),
    SUNFLOWER("Подсолнечник"),
    SOY("Соя"),
    APPLES("Яблоки"),
    APPLE_JUICE("Яблочный сок");

    private final String productName;

    @JsonValue
    public String getProductName() {
        return productName;
    }

    @JsonCreator
    public static Product fromString(String value) {
        return Arrays.stream(Product.values())
                .filter(product -> product.getProductName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidProductException(value));
    }

}
