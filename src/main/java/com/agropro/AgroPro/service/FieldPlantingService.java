package com.agropro.AgroPro.service;

import java.time.LocalDate;

public interface FieldPlantingService {

    void createFieldPlanting(Long fieldId, String cropType, LocalDate plantingDate);

    void addHarvestDate(Long fieldId, LocalDate harvestDate);

//    List<FieldPlantingView> getFieldPlantingsByFieldId(Long fieldId);

}
