package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.GenderNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum Gender {

    MALE("Мужской"),
    FEMALE("Женский");

    private final String gender;

    private static final Map<String, Gender> ENUM_MAP = new HashMap<>();

    static {
        for (Gender g : Gender.values()) {
            ENUM_MAP.put(g.getGender(), g);
        }
    }

    public static Gender fromString(String gender) {
        Gender g = ENUM_MAP.get(gender);
        if (g == null) {
            throw new GenderNotFoundException(gender);
        }
        return g;
    }

}
