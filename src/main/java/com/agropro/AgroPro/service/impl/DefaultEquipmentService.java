package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.mapper.EquipmentMapper;
import com.agropro.AgroPro.repository.EquipmentRepository;
import com.agropro.AgroPro.service.EquipmentService;
import com.agropro.AgroPro.service.EquipmentStatusHistoryService;
import com.agropro.AgroPro.service.EquipmentTypeService;
import com.agropro.AgroPro.service.StatusService;
import com.agropro.AgroPro.view.EquipmentBasicInfoView;
import com.agropro.AgroPro.view.EquipmentView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultEquipmentService implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentTypeService equipmentTypeService;
    private final StatusService statusService;
    private final EquipmentStatusHistoryService equipmentStatusHistoryService;

    @Override
    @Transactional
    public void addEquipment(EquipmentForm equipmentForm) {
        equipmentTypeService.validateEquipmentTypeExistsById(equipmentForm.getEquipmentTypeId());
        Long idleStatusId = statusService.getIdleStatusCodeId();

        Long equipmentId = equipmentRepository.save(EquipmentMapper.toModel(equipmentForm, idleStatusId));

        equipmentStatusHistoryService.addHistoryRecord(equipmentId, idleStatusId);
    }

    @Override
    public List<EquipmentView> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public List<EquipmentBasicInfoView> getIdleEquipment() {
        return equipmentRepository.findEquipmentWithIdleStatus();
    }
}
