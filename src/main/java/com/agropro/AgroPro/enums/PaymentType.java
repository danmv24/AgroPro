package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.PaymentTypeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    FIXED("Фиксированная"),
    HOURLY("Почасовая");

    private final String paymentType;

    private static final Map<String, PaymentType> ENUM_MAP = new HashMap<>();

    static {
        for (PaymentType paymentType : PaymentType.values()) {
            ENUM_MAP.put(paymentType.getPaymentType(), paymentType);
        }
    }

    public static PaymentType fromString(String paymentType) {
        PaymentType paymentTypeName = ENUM_MAP.get(paymentType);
        if (paymentTypeName == null) {
            throw new PaymentTypeNotFoundException(paymentType);
        }
        return paymentTypeName;
    }

}
