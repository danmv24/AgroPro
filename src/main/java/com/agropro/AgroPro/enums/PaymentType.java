package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.PaymentTypeNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    FIXED("Фиксированная"),
    HOURLY("Почасовая");

    private final String paymentTypeName;

    @JsonValue
    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    @JsonCreator
    public static PaymentType fromString(String value) {
        return Arrays.stream(PaymentType.values())
                .filter(pt -> pt.getPaymentTypeName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new PaymentTypeNotFoundException(value));
    }

}
