package com.agropro.AgroPro.service.impl;


import com.agropro.AgroPro.exception.MachineryTypeNotFoundException;
import com.agropro.AgroPro.mapper.MachineryTypeMapper;
import com.agropro.AgroPro.model.MachineryType;
import com.agropro.AgroPro.repository.MachineryTypeRepository;
import com.agropro.AgroPro.service.MachineryTypeService;
import com.agropro.AgroPro.view.MachineryTypeView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMachineryTypeService implements MachineryTypeService {

    private final MachineryTypeRepository machineryTypeRepository;

    @Override
    public List<MachineryTypeView> getAllMachineryTypes() {
        List<MachineryType> machineryTypes = machineryTypeRepository.findAll();

        return machineryTypes.stream()
                .map(MachineryTypeMapper::toView)
                .toList();
    }

    @Override
    public void validateMachineryTypeExistsById(Long machineryTypeId) {
        if (!machineryTypeRepository.existsById(machineryTypeId)) {
            throw new MachineryTypeNotFoundException(HttpStatus.NOT_FOUND, machineryTypeId);
        }
    }


}
