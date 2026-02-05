package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.enums.FieldWorkStatus;
import com.agropro.AgroPro.exception.FieldWorkCannotBeCancelledException;
import com.agropro.AgroPro.exception.FieldWorkNotFoundException;
import com.agropro.AgroPro.form.FieldWorkForm;
import com.agropro.AgroPro.mapper.FieldWorkMapper;
import com.agropro.AgroPro.repository.FieldWorkRepository;
import com.agropro.AgroPro.service.*;
import com.agropro.AgroPro.view.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    @Override
    public List<FieldWorkBasicInfoView> getFieldWorks() {
        return fieldWorkRepository.findAll();
    }

    @Override
    public FieldWorkView getFieldWork(Long workId) {
        FieldWorkDetail fieldWorkDetail = fieldWorkRepository.findFieldWorkById(workId).orElseThrow(() -> new FieldWorkNotFoundException(workId));
        List<EmployeeBasicInfoView> employees = employeeService.getEmployeesByFieldWorkId(workId);
        List<MachineryBasicInfoView> machineries = machineryService.getMachineriesByFieldWorkId(workId);
        List<EquipmentBasicInfoView> equipment = equipmentService.getEquipmentByFieldWorkId(workId);

        FieldWorkView fieldWorkView = FieldWorkMapper.toView(fieldWorkDetail, employees, machineries, equipment);

        return fieldWorkView;
    }

    @Override
    public void cancelFieldWork(Long workId) {
        FieldWorkStatus status = fieldWorkRepository.getStatusByFieldWorkId(workId).orElseThrow(); // сделать искл

        if (status == FieldWorkStatus.COMPLETED || status == FieldWorkStatus.CANCELLED) {
            throw new FieldWorkCannotBeCancelledException(workId, status);
        }

        fieldWorkRepository.updateStatusById(workId, FieldWorkStatus.CANCELLED);
    }


    private void validateEntitiesExist(FieldWorkForm fieldWorkForm) {
        employeeService.validateEmployeesExistByIds(fieldWorkForm.getEmployeeIds());
        machineryService.validateMachineriesExistByIds(fieldWorkForm.getMachineryIds());

        if (fieldWorkForm.getEquipmentIds() != null && !fieldWorkForm.getEquipmentIds().isEmpty()) {
            equipmentService.validateEquipmentExistByIds(fieldWorkForm.getEquipmentIds());
        }

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
        fieldWorkRepository.linkEmployees(workId, fieldWorkForm.getEmployeeIds());
        fieldWorkRepository.linkMachineries(workId, fieldWorkForm.getMachineryIds());

        if (fieldWorkForm.getEquipmentIds() != null && !fieldWorkForm.getEquipmentIds().isEmpty()) {
            fieldWorkRepository.linkEquipment(workId, fieldWorkForm.getEquipmentIds());
        }
    }

}
