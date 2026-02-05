package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.EquipmentTypeNotFoundException;
import com.agropro.AgroPro.mapper.EquipmentTypeMapper;
import com.agropro.AgroPro.model.EquipmentType;
import com.agropro.AgroPro.repository.EquipmentTypeRepository;
import com.agropro.AgroPro.service.EquipmentTypeService;
import com.agropro.AgroPro.view.EquipmentTypeView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultEquipmentTypeService implements EquipmentTypeService {

    private final EquipmentTypeRepository equipmentTypeRepository;

    @Override
    public List<EquipmentTypeView> getAllEquipmentTypes() {
        List<EquipmentType> equipmentTypes = equipmentTypeRepository.findAll();

        return equipmentTypes.stream()
                .map(EquipmentTypeMapper::toView)
                .toList();
    }

    @Override
    public void validateEquipmentTypeExistsById(Long id) {
        if (!equipmentTypeRepository.existsById(id)) {
            throw new EquipmentTypeNotFoundException(id);
        }
    }
}
