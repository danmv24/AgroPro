package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.GenderNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Gender {

    MALE("Мужской"),
    FEMALE("Женский");

    private final String genderName;

    @JsonValue
    public String getGenderName() {
        return genderName;
    }

    @JsonCreator
    public static Gender fromString(String value) {
        return Arrays.stream(Gender.values())
                .filter(g -> g.getGenderName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new GenderNotFoundException(value));
    }

}
