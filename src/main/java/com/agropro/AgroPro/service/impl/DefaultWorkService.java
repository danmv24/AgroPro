package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.dto.request.WorkRequest;
import com.agropro.AgroPro.dto.request.WorkResultRequest;
import com.agropro.AgroPro.dto.response.*;
import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import com.agropro.AgroPro.exception.WorkCannotBeCancelledException;
import com.agropro.AgroPro.exception.WorkNotFoundException;
import com.agropro.AgroPro.exception.WorkResultNotAllowedException;
import com.agropro.AgroPro.exception.WorkResultValidationException;
import com.agropro.AgroPro.mapper.*;
import com.agropro.AgroPro.model.*;
import com.agropro.AgroPro.repository.*;
import com.agropro.AgroPro.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
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
    private final WorkResultRepository workResultRepository;

    @Override
    @Transactional
    public void createWork(WorkRequest workRequest) {
        validateEntitiesExist(workRequest);
        validateAvailability(workRequest);

        Work work = workRepository.save(WorkMapper.toModel(workRequest));

        linkEntities(work.getId(), workRequest);

        if (work.getWorkType() == WorkType.SOWING) {
            if (workRequest.getCrop() == null) {
                throw new IllegalArgumentException("Для посева необходимо указать культуру");
            }
            fieldPlantingService.createFieldPlanting(work.getFieldId(), workRequest.getCrop(), work.getEndDate().toLocalDate());
        }

        if (work.getWorkType() == WorkType.HARVESTING) {
            fieldPlantingService.addHarvestDate(work.getFieldId(), workRequest.getEndDate().toLocalDate());
        }
    }

    @Override
    public Slice<WorkBasicInfoResponse> getWorksByStatus(WorkStatus workStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("endDate").descending());

        Slice<Work> works = workRepository.findWorksByStatus(workStatus, pageable);
        boolean isNeedResult = workStatus == WorkStatus.COMPLETED;

        return linkFieldAndResultInfo(works, isNeedResult);
    }

    private Slice<WorkBasicInfoResponse> linkFieldAndResultInfo(Slice<Work> works, boolean checkResults) {
        Set<Long> fieldIds = works.stream()
                .map(Work::getFieldId)
                .collect(Collectors.toSet());

        Map<Long, Field> fieldsById = fieldService.getFieldsByIds(fieldIds).stream()
                .collect(Collectors.toMap(
                        Field::getId, Function.identity()
                ));

        Set<Long> workIds = works.stream()
                .map(Work::getId)
                .collect(Collectors.toSet());

        Set<Long> workIdsWithResult = checkResults ? workResultRepository.findExistingResultWorkIds(workIds) : Set.of();

        return works.map(work -> {
            Field field = fieldsById.get(work.getFieldId());
            boolean hasResult = workIdsWithResult.contains(work.getId());

            return WorkMapper.toBasicInfoView(work, field, hasResult);
        });
    }

    @Override
    public WorkResponse getWorkDetail(Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new WorkNotFoundException(workId));
        Field field = fieldService.getFieldById(work.getFieldId());

        List<EmployeeBasicInfoResponse> employees = employeeService.getEmployeesByWorkId(workId);
        List<MachineryBasicInfoResponse> machineries = machineryService.getMachineriesByWorkId(workId);
        List<EquipmentBasicInfoResponse> equipment = equipmentService.getEquipmentByWorkId(workId);

        return WorkMapper.toView(work, field, employees, machineries, equipment);
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

    @Override
    public void createResult(Long workId, WorkResultRequest workResultRequest) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new WorkNotFoundException(workId));

        if (work.getStatus() != WorkStatus.COMPLETED) {
            throw new WorkResultNotAllowedException(workId, work.getStatus());
        }

        validateByWorkType(work.getWorkType(), workResultRequest);

        workResultRepository.save(WorkResultMapper.toModel(workId, workResultRequest));
    }

//    @Override
//    public void updateWork(Long workId, WorkUpdateForm workUpdateForm) {
//        Work work = workRepository.findById(workId).orElseThrow(() -> new WorkNotFoundException(workId));
//
//    }

    private void validateEntitiesExist(WorkRequest workRequest) {
        employeeService.validateEmployeesExistByIds(workRequest.getEmployeeIds());
        machineryService.validateMachineriesExistByIds(workRequest.getMachineryIds());
        if (workRequest.getEquipmentIds() != null && !workRequest.getEquipmentIds().isEmpty()) {
            equipmentService.validateEquipmentExistByIds(workRequest.getEquipmentIds());
        }

        fieldService.validateFieldExistsById(workRequest.getFieldId());
    }

    private void validateAvailability(WorkRequest workRequest) {
        employeeService.validateEmployeesAvailability(workRequest.getEmployeeIds(), workRequest.getStartDate(), workRequest.getEndDate());
        machineryService.validateMachineriesAvailability(workRequest.getMachineryIds(), workRequest.getStartDate(), workRequest.getEndDate());
        if (workRequest.getEquipmentIds() != null && !workRequest.getEquipmentIds().isEmpty()) {
            equipmentService.validateEquipmentAvailability(workRequest.getEquipmentIds(), workRequest.getStartDate(), workRequest.getEndDate());
        }
    }

    private void linkEntities(Long workId, WorkRequest workRequest) {
        List<WorkEmployee> employees = workRequest.getEmployeeIds().stream()
                .map(employeeId -> WorkEmployeeMapper.toModel(workId, employeeId))
                .toList();

        List<WorkMachinery> machineries = workRequest.getMachineryIds().stream()
                .map(machineryId -> WorkMachineryMapper.toModel(workId, machineryId))
                .toList();

        if (workRequest.getEquipmentIds() != null && !workRequest.getEquipmentIds().isEmpty()) {
            List<WorkEquipment> equipment = workRequest.getEquipmentIds().stream()
                    .map(equipmentId -> WorkEquipmentMapper.toModel(workId, equipmentId))
                    .toList();
            workEquipmentRepository.saveAll(equipment);
        }

        workEmployeeRepository.saveAll(employees);
        workMachineryRepository.saveAll(machineries);
    }

    private void validateByWorkType(WorkType workType, WorkResultRequest workResultRequest) {
        if (workResultRequest.getFuelUsed() == null) {
            throw new WorkResultValidationException("Не указан расход топлива");
        }

        switch (workType) {
            case SOWING -> {
                if (workResultRequest.getSeedsUsed() == null) {
                    throw new WorkResultValidationException("Для посева необходимо указать расход семян");
                }
            }
            case HARVESTING -> {
                if (workResultRequest.getYield() == null) {
                    throw new WorkResultValidationException("Для уборки необходимо указать объем урожая");
                }
            }
            case FERTILIZING -> {
                if (workResultRequest.getFertilizerType() == null || workResultRequest.getFertilizersUsed() == null) {
                    throw new WorkResultValidationException("Необходимо указать тип и количество удобрений");
                }
            }
        }
    }

}
