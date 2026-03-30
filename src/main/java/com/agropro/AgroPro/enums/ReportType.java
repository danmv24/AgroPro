package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.InvalidReportTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum ReportType {

    APK_5("АПК-5"),
    APK_8("АПК-8"),
    APK_9("АПК-9"),
    APK_17("АПК-17");

    private final String reportTypeName;

    @JsonValue
    public String getReportTypeName() {
        return reportTypeName;
    }

    @JsonCreator
    public static ReportType fromString(String value) {
        return Arrays.stream(ReportType.values())
                .filter(rt -> rt.getReportTypeName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidReportTypeException(value));
    }

}
