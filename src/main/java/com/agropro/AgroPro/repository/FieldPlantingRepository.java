package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.view.FieldPlantingView;

import java.util.List;


public interface FieldPlantingRepository {

    void save(FieldPlanting fieldPlanting);

    List<FieldPlantingView> findPlantingsByFieldId(Long fieldId);

}
