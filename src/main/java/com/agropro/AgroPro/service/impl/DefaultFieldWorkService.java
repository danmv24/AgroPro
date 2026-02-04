package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.form.FieldWorkForm;
import com.agropro.AgroPro.mapper.FieldWorkMapper;
import com.agropro.AgroPro.repository.FieldWorkRepository;
import com.agropro.AgroPro.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DefaultFieldWorkService implements FieldWorkService {

    private final FieldWorkRepository fieldWorkRepository;
    private final EmployeeService employeeService;
    private final MachineryService machineryService;
    private final EquipmentService equipmentService;
    private final FieldService fieldService;
    private final WorkTypeService workTypeService;

    @Override
    @Transactional
    public void addFieldWork(FieldWorkForm fieldWorkForm) {
        validateEntitiesExist(fieldWorkForm);
        validateStatus(fieldWorkForm);
        validateAvailability(fieldWorkForm);

        Long workId = fieldWorkRepository.save(FieldWorkMapper.toModel(fieldWorkForm));

        linkEntities(workId, fieldWorkForm);
    }


    private void validateEntitiesExist(FieldWorkForm fieldWorkForm) {
        employeeService.validateEmployeesExistByIds(fieldWorkForm.getEmployeeIds());
        machineryService.validateMachineriesExistByIds(fieldWorkForm.getMachineryIds());
        equipmentService.validateEquipmentExistByIds(fieldWorkForm.getEquipmentIds());
        fieldService.validateFieldExistsById(fieldWorkForm.getFieldId());
        workTypeService.validateWorkTypeExistById(fieldWorkForm.getWorkTypeId());
    }

    private void validateStatus(FieldWorkForm fieldWorkForm) {
        machineryService.validateMachineryStatus(fieldWorkForm.getMachineryIds());
        equipmentService.validateEquipmentStatus(fieldWorkForm.getEquipmentIds());
    }

    private void validateAvailability(FieldWorkForm fieldWorkForm) {
        employeeService.validateEmployeesAvailability(fieldWorkForm.getEmployeeIds(), fieldWorkForm.getStartDate(), fieldWorkForm.getEndDate());
        machineryService.validateMachineriesAvailability(fieldWorkForm.getMachineryIds(), fieldWorkForm.getStartDate(), fieldWorkForm.getEndDate());
        equipmentService.validateEquipmentAvailability(fieldWorkForm.getEquipmentIds(), fieldWorkForm.getStartDate(), fieldWorkForm.getEndDate());
    }

    private void linkEntities(Long workId, FieldWorkForm fieldWorkForm) {
        if (fieldWorkForm.getEmployeeIds() != null && !fieldWorkForm.getEmployeeIds().isEmpty()) {
            fieldWorkRepository.linkEmployees(workId, fieldWorkForm.getEmployeeIds());
        }

        if (fieldWorkForm.getMachineryIds() != null && !fieldWorkForm.getMachineryIds().isEmpty()) {
            fieldWorkRepository.linkMachineries(workId, fieldWorkForm.getMachineryIds());
        }

        if (fieldWorkForm.getEquipmentIds() != null && !fieldWorkForm.getEquipmentIds().isEmpty()) {
            fieldWorkRepository.linkEquipment(workId, fieldWorkForm.getEquipmentIds());
        }
    }

}
