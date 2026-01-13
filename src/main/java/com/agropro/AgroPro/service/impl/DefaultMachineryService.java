package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.mapper.MachineryMapper;
import com.agropro.AgroPro.repository.MachineryRepository;
import com.agropro.AgroPro.service.MachineryService;
import com.agropro.AgroPro.service.MachineryTypeService;
import com.agropro.AgroPro.service.StatusService;
import com.agropro.AgroPro.view.MachineryView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMachineryService implements MachineryService {

    private final MachineryRepository machineryRepository;

    private final MachineryTypeService machineryTypeService;

    private final StatusService statusService;

    @Override
    public void addMachinery(MachineryForm machineryForm) {
        machineryTypeService.validateExists(machineryForm.getMachineryTypeId());
        Long idleStatusId = statusService.getIdleStatusCodeId();

        machineryRepository.save(MachineryMapper.toModel(machineryForm, idleStatusId));
    }

    @Override
    public List<MachineryView> getMachineries() {
        return machineryRepository.findAll();
    }

}
