package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import com.agropro.AgroPro.exception.WorkCannotBeCancelledException;
import com.agropro.AgroPro.exception.WorkNotFoundException;
import com.agropro.AgroPro.form.WorkForm;
import com.agropro.AgroPro.mapper.WorkEmployeeMapper;
import com.agropro.AgroPro.mapper.WorkEquipmentMapper;
import com.agropro.AgroPro.mapper.WorkMachineryMapper;
import com.agropro.AgroPro.mapper.WorkMapper;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.model.WorkEmployee;
import com.agropro.AgroPro.model.WorkEquipment;
import com.agropro.AgroPro.model.WorkMachinery;
import com.agropro.AgroPro.repository.WorkEmployeeRepository;
import com.agropro.AgroPro.repository.WorkEquipmentRepository;
import com.agropro.AgroPro.repository.WorkMachineryRepository;
import com.agropro.AgroPro.repository.WorkRepository;
import com.agropro.AgroPro.service.*;
import com.agropro.AgroPro.view.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DefaultWorkService implements WorkService {

    private final EmployeeService employeeService;
    private final MachineryService machineryService;
    private final EquipmentService equipmentService;
    private final FieldService fieldService;
    private final FieldPlantingService fieldPlantingService;

    private final WorkRepository workRepository;
    private final WorkEmployeeRepository workEmployeeRepository;
    private final WorkEquipmentRepository workEquipmentRepository;
    private final WorkMachineryRepository workMachineryRepository;

    @Override
    @Transactional
    public void createWork(WorkForm workForm) {
        validateEntitiesExist(workForm);
        validateAvailability(workForm);

        Work work = workRepository.save(WorkMapper.toModel(workForm));

        linkEntities(work.getId(), workForm);

        if (work.getWorkType() == WorkType.SOWING) {
            if (workForm.getCropName() == null || workForm.getCropName().isBlank()) {
                throw new IllegalArgumentException("Для посева необходимо указать культуру");
            }
            fieldPlantingService.createFieldPlanting(work.getFieldId(), workForm.getCropName(), work.getEndDate().toLocalDate());
        }

        if (work.getWorkType() == WorkType.HARVESTING) {
            fieldPlantingService.addHarvestDate(work.getFieldId(), workForm.getEndDate().toLocalDate());
        }
    }

    @Override
    public List<WorkBasicInfoView> getWorks() {
        return workRepository.findAllBasicInfo();

    }

    @Override
    public WorkView getWork(Long workId) {
        FieldWorkDetail fieldWorkDetail = workRepository.findFieldWorkDetailByFieldWorkId(workId).orElseThrow(() -> new WorkNotFoundException(workId));
        List<EmployeeBasicInfoView> employees = employeeService.getEmployeesByFieldWorkId(workId);
        List<MachineryBasicInfoView> machineries = machineryService.getMachineriesByFieldWorkId(workId);
        List<EquipmentBasicInfoView> equipment = equipmentService.getEquipmentByFieldWorkId(workId);

        return WorkMapper.toView(fieldWorkDetail, employees, machineries, equipment);
    }

    @Override
    @Transactional
    public void cancelWork(Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(
                () -> new WorkNotFoundException(workId));

        if (work.getStatus() == WorkStatus.COMPLETED || work.getStatus() == WorkStatus.CANCELLED) {
            throw new WorkCannotBeCancelledException(workId, work.getStatus());
        }

        LocalDateTime now = LocalDateTime.now();
        Set<Long> workIds = Set.of(workId);

        work.setStatus(WorkStatus.CANCELLED);
        workRepository.save(work);

        machineryService.changeMachineryStatusByWorkIds(workIds, StatusCode.IDLE, now);
        equipmentService.changeEquipmentStatusByWorkIds(workIds, StatusCode.IDLE, now);
    }

    @Override
    @Transactional
    public void updateStatuses() {
        LocalDateTime now = LocalDateTime.now();

        List<Work> worksToStart = workRepository.findWorksToStart(WorkStatus.PLANNED, now);
        if (!worksToStart.isEmpty()) {
            worksToStart.forEach(work -> work.setStatus(WorkStatus.IN_PROGRESS));
            workRepository.saveAll(worksToStart);

            Set<Long> workIds = worksToStart.stream().map(Work::getId).collect(Collectors.toSet());
            machineryService.changeMachineryStatusByWorkIds(workIds, StatusCode.IN_OPERATION, now);
            equipmentService.changeEquipmentStatusByWorkIds(workIds, StatusCode.IN_OPERATION, now);
        }

        List<Work> worksToCompleted = workRepository.findWorksToCompleted(WorkStatus.IN_PROGRESS, now);
        if (!worksToCompleted.isEmpty()) {
            worksToCompleted.forEach(work -> work.setStatus(WorkStatus.COMPLETED));
            workRepository.saveAll(worksToCompleted);

            Set<Long> workIds = worksToCompleted.stream().map(Work::getId).collect(Collectors.toSet());
            machineryService.changeMachineryStatusByWorkIds(workIds, StatusCode.IDLE, now);
            equipmentService.changeEquipmentStatusByWorkIds(workIds, StatusCode.IDLE, now);
        }

    }

    private void validateEntitiesExist(WorkForm workForm) {
        employeeService.validateEmployeesExistByIds(workForm.getEmployeeIds());
        machineryService.validateMachineriesExistByIds(workForm.getMachineryIds());
        if (workForm.getEquipmentIds() != null && !workForm.getEquipmentIds().isEmpty()) {
            equipmentService.validateEquipmentExistByIds(workForm.getEquipmentIds());
        }

        fieldService.validateFieldExistsById(workForm.getFieldId());
    }

    private void validateAvailability(WorkForm workForm) {
        employeeService.validateEmployeesAvailability(workForm.getEmployeeIds(), workForm.getStartDate(), workForm.getEndDate());
        machineryService.validateMachineriesAvailability(workForm.getMachineryIds(), workForm.getStartDate(), workForm.getEndDate());
        if (workForm.getEquipmentIds() != null && !workForm.getEquipmentIds().isEmpty()) {
            equipmentService.validateEquipmentAvailability(workForm.getEquipmentIds(), workForm.getStartDate(), workForm.getEndDate());
        }
    }

    private void linkEntities(Long workId, WorkForm workForm) {
        List<WorkEmployee> employees = workForm.getEmployeeIds().stream()
                .map(employeeId -> WorkEmployeeMapper.toModel(workId, employeeId))
                .toList();

        List<WorkMachinery> machineries = workForm.getMachineryIds().stream()
                .map(machineryId -> WorkMachineryMapper.toModel(workId, machineryId))
                .toList();

        if (workForm.getEquipmentIds() != null && !workForm.getEquipmentIds().isEmpty()) {
            List<WorkEquipment> equipment = workForm.getEquipmentIds().stream()
                    .map(equipmentId -> WorkEquipmentMapper.toModel(workId, equipmentId))
                    .toList();
            workEquipmentRepository.saveAll(equipment);
        }

        workEmployeeRepository.saveAll(employees);
        workMachineryRepository.saveAll(machineries);
    }

}
