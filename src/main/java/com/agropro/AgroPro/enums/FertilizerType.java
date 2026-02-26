package com.agropro.AgroPro.enums;

import com.agropro.AgroPro.exception.FertilizerTypeNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FertilizerType {

    NITROGEN("Азотные", FertilizerCategory.MINERAL),
    PHOSPHATE("Фосфорные", FertilizerCategory.MINERAL),
    POTASSIUM("Калийные", FertilizerCategory.MINERAL),
    NPK_COMPLEX("NPK комплексные", FertilizerCategory.MINERAL),
    AMMONIUM_NITRATE("Аммиачная селитра", FertilizerCategory.MINERAL),
    UREA("Карбамид", FertilizerCategory.MINERAL),
    SUPERPHOSPHATE("Суперфосфат", FertilizerCategory.MINERAL),
    POTASH_SALT("Калийная соль", FertilizerCategory.MINERAL),
    MICRO_NUTRIENT("Микроудобрения", FertilizerCategory.MICRO),
    LIME("Известковые", FertilizerCategory.SOIL_AMENDMENT);

    private final String fertilizerTypeName;
    private final FertilizerCategory category;

    @JsonValue
    public String getFertilizerTypeName() {
        return fertilizerTypeName;
    }

    @JsonCreator
    public static FertilizerType fromString(String value) {
        return Arrays.stream(FertilizerType.values())
                .filter(ft -> ft.getFertilizerTypeName().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new FertilizerTypeNotFoundException(value));
    }

}
