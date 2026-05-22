package com.agropro.AgroPro.service;

import com.agropro.AgroPro.controller.FieldPlantingResponse;
import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.model.FieldPlanting;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface FieldPlantingService {

    void createFieldPlanting(Long fieldId, CropType crop, LocalDate plantingDate);

    void addHarvestDate(Long fieldId, LocalDate harvestDate);

    List<FieldPlanting> getPlantingsByIdsAndDate(Set<Long> fieldIds, LocalDate date);

    Slice<FieldPlantingResponse> getFieldPlantingHistory(Long fieldId, int page, int size);

}
