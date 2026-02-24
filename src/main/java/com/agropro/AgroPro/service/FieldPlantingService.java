package com.agropro.AgroPro.service;

import com.agropro.AgroPro.model.FieldPlanting;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface FieldPlantingService {

    void createFieldPlanting(Long fieldId, String cropType, LocalDate plantingDate);

    void addHarvestDate(Long fieldId, LocalDate harvestDate);

    List<FieldPlanting> getPlantingsByIdsAndDate(Set<Long> fieldIds, LocalDate date);

//    List<FieldPlantingView> getFieldPlantingsByFieldId(Long fieldId);

}
