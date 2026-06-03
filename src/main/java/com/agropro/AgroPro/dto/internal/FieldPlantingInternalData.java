package com.agropro.AgroPro.dto.internal;

import com.agropro.AgroPro.enums.CropType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldPlantingInternalData {

    private final Long id;

    private final Long fieldId;

    private final CropType cropType;

    private final LocalDate plantingDate;

    private final LocalDate harvestDate;

}
