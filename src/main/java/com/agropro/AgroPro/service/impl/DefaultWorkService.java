package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import com.agropro.AgroPro.exception.WorkCannotBeCancelledException;
import com.agropro.AgroPro.exception.WorkNotFoundException;
import com.agropro.AgroPro.exception.WorkResultNotAllowedException;
import com.agropro.AgroPro.exception.WorkResultValidationException;
import com.agropro.AgroPro.form.WorkForm;
import com.agropro.AgroPro.form.WorkResultForm;
import com.agropro.AgroPro.form.WorkUpdateForm;
import com.agropro.AgroPro.mapper.*;
import com.agropro.AgroPro.model.*;
import com.agropro.AgroPro.repository.*;
import com.agropro.AgroPro.service.*;
import com.agropro.AgroPro.view.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

//    @Override
//    public List<WorkBasicInfoView> getWorks() {
//        List<Work> works = workRepository.findAll();
//        Set<Long> fieldIds = works.stream()
//                .map(Work::getFieldId)
//                .collect(Collectors.toSet());
//
//        Map<Long, Field> fieldsById = fieldService.getFieldsByIds(fieldIds).stream()
//                .collect(Collectors.toMap(
//                        Field::getId, Function.identity()
//                ));
//
//        return works.stream()
//                .map(work -> {
//                    Field field = fieldsById.get(work.getFieldId());
//
//                    return WorkMapper.toBasicInfoView(work, field, false);
//                }).toList();
//    }

//    @Override
//    public Slice<WorkBasicInfoView> getCompletedWorksForWeek(LocalDate weekStart, int page, int size) {
//        LocalDateTime startDate = weekStart.atStartOfDay();
//        LocalDateTime endDate = weekStart.plusDays(7).atStartOfDay();
//        Pageable pageable = PageRequest.of(page, size, Sort.by("endDate").descending());
//
//        Slice<Work> works = workRepository.findCompletedWorksByDateRange(WorkStatus.COMPLETED, startDate, endDate, pageable);
//
//        Set<Long> fieldIds = works.stream()
//                .map(Work::getFieldId)
//                .collect(Collectors.toSet());
//
//        Map<Long, Field> fieldsById = fieldService.getFieldsByIds(fieldIds).stream()
//                .collect(Collectors.toMap(
//                        Field::getId, Function.identity()
//                ));
//
//        Set<Long> workIds = works.stream()
//                .map(Work::getId)
//                .collect(Collectors.toSet());
//
//        Set<Long> workIdsWithResult = workResultRepository.findExistingResultWorkIds(workIds);
//
//        return works.map(work -> {
//            Field field = fieldsById.get(work.getFieldId());
//            boolean hasResult = workIdsWithResult.contains(work.getId());
//
//            return WorkMapper.toBasicInfoView(work, field, hasResult);
//        });
//
//    }

    @Override
    public WorkByStatusView getWorksByStatus(LocalDate weekStart, int page, int size) {
        LocalDateTime startDate = weekStart.atStartOfDay();
        LocalDateTime endDate = weekStart.plusDays(7).atStartOfDay();
        Pageable pageable = PageRequest.of(page, size, Sort.by("endDate").descending());

        Slice<Work> planned = workRepository.findSliceByStatusAndEndDateGreaterThanEqualAndEndDateLessThan(
                WorkStatus.PLANNED, startDate, endDate, pageable);
        Slice<Work> inProgress = workRepository.findSliceByStatusAndEndDateGreaterThanEqualAndEndDateLessThan(
                WorkStatus.IN_PROGRESS, startDate, endDate, pageable);
        Slice<Work> completed = workRepository.findSliceByStatusAndEndDateGreaterThanEqualAndEndDateLessThan(
                WorkStatus.COMPLETED, startDate, endDate, pageable);

        Slice<WorkBasicInfoView> plannedView = linkWorksWithFieldAndResultInfo(planned, false);
        Slice<WorkBasicInfoView> inProgressView = linkWorksWithFieldAndResultInfo(inProgress, false);
        Slice<WorkBasicInfoView> completedView = linkWorksWithFieldAndResultInfo(completed, true);

        return WorkMapper.toWorkByStatusView(plannedView, inProgressView, completedView);
    }

    private Slice<WorkBasicInfoView> linkWorksWithFieldAndResultInfo(Slice<Work> works, boolean checkResults) {
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
    public WorkView getWorkDetail(Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new WorkNotFoundException(workId));
        Field field = fieldService.getFieldById(work.getFieldId());

        List<EmployeeBasicInfoView> employees = employeeService.getEmployeesByWorkId(workId);
        List<MachineryBasicInfoView> machineries = machineryService.getMachineriesByWorkId(workId);
        List<EquipmentBasicInfoView> equipment = equipmentService.getEquipmentByWorkId(workId);

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
    public void createResult(Long workId, WorkResultForm workResultForm) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new WorkNotFoundException(workId));

        if (work.getStatus() != WorkStatus.COMPLETED) {
            throw new WorkResultNotAllowedException(workId, work.getStatus());
        }

        validateByWorkType(work.getWorkType(), workResultForm);

        workResultRepository.save(WorkResultMapper.toModel(workId, workResultForm));
    }

    @Override
    public void updateWork(Long workId, WorkUpdateForm workUpdateForm) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new WorkNotFoundException(workId));

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

    private void validateByWorkType(WorkType workType, WorkResultForm workResultForm) {
        if (workResultForm.getFuelUsed() == null) {
            throw new WorkResultValidationException("Не указан расход топлива");
        }

        switch (workType) {
            case SOWING -> {
                if (workResultForm.getSeedsUsed() == null) {
                    throw new WorkResultValidationException("Для посева необходимо указать расход семян");
                }
            }
            case HARVESTING -> {
                if (workResultForm.getHarvestAmount() == null) {
                    throw new WorkResultValidationException("Для уборки необходимо указать объем урожая");
                }
            }
            case FERTILIZING -> {
                if (workResultForm.getFertilizerType() == null || workResultForm.getFertilizerAmount() == null) {
                    throw new WorkResultValidationException("Необходимо указать тип и количество удобрений");
                }
            }
            case WATERING -> {
                if (workResultForm.getWaterAmount() == null) {
                    throw new WorkResultValidationException("Необходимо указать объем воды");
                }
            }
        }
    }

}
