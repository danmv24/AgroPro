package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.mapper.MachineryMapper;
import com.agropro.AgroPro.repository.MachineryRepository;
import com.agropro.AgroPro.service.MachineryService;
import com.agropro.AgroPro.service.MachineryStatusHistoryService;
import com.agropro.AgroPro.service.MachineryTypeService;
import com.agropro.AgroPro.service.StatusService;
import com.agropro.AgroPro.view.MachineryView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMachineryService implements MachineryService {

    private final MachineryRepository machineryRepository;
    private final MachineryTypeService machineryTypeService;
    private final StatusService statusService;
    private final MachineryStatusHistoryService machineryStatusHistoryService;

    @Transactional
    @Override
    public void addMachinery(MachineryForm machineryForm) {
        machineryTypeService.validateMachineryTypeExistsById(machineryForm.getMachineryTypeId());
        Long idleStatusId = statusService.getIdleStatusCodeId();

        Long machineryId = machineryRepository.save(MachineryMapper.toModel(machineryForm, idleStatusId));

        machineryStatusHistoryService.addHistoryRecord(machineryId, idleStatusId);
    }

    @Override
    public List<MachineryView> getMachineries() {
        return machineryRepository.findAll();
    }

}
