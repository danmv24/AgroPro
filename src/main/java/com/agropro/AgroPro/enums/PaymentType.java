package com.agropro.AgroPro.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    FIXED("Фиксированная"),
    HOURLY("Почасовая");

    private final String paymentTypeName;

}
