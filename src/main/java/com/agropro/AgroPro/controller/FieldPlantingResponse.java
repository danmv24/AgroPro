package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.enums.CropType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldPlantingResponse {

    private final CropType cropType;

    private final LocalDate plantingDate;

    private final LocalDate harvestDate;

}
