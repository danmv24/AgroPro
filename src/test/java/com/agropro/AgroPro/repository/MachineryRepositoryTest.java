package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.MachineryType;
import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.model.MachineryStatusHistory;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.model.WorkMachinery;
import com.agropro.AgroPro.projection.MachineryTypeCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest

public class MachineryRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private MachineryRepository machineryRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkMachineryRepository workMachineryRepository;

    @Autowired
    private MachineryStatusHistoryRepository machineryStatusHistoryRepository;

    private Machinery tractor;

    private Machinery combine;

    private Machinery decommissionedTractor;

    private Work plannedWork;

    private Work completedWork;

    @BeforeEach
    void setUp() {
        tractor = machineryRepository.save(Machinery.builder()
                .machineryName("Трактор №1")
                .machineryType(MachineryType.TRACTOR)
                .inventoryNumber(1)
                .licensePlate("А111АА")
                .currentStatus(StatusCode.IN_OPERATION)
                .purchaseDate(LocalDate.of(2024, 5, 10))
                .build());

        combine = machineryRepository.save(Machinery.builder()
                .machineryName("Комбайн №1")
                .machineryType(MachineryType.COMBINE)
                .inventoryNumber(2)
                .licensePlate("Б222ББ")
                .currentStatus(StatusCode.IDLE)
                .purchaseDate(LocalDate.of(2025, 3, 15))
                .build());

        decommissionedTractor = machineryRepository.save(Machinery.builder()
                .machineryName("Трактор №3")
                .machineryType(MachineryType.TRACTOR)
                .inventoryNumber(1003)
                .licensePlate("В333ВВ")
                .currentStatus(StatusCode.DECOMMISSIONED)
                .purchaseDate(LocalDate.of(2020, 8, 1))
                .build());

        machineryStatusHistoryRepository.saveAll(List.of(
                MachineryStatusHistory.builder()
                        .machineryId(tractor.getId())
                        .status(StatusCode.IN_OPERATION)
                        .changedAt(LocalDateTime.of(2026, 5, 14, 8, 0))
                        .build(),
                MachineryStatusHistory.builder()
                        .machineryId(combine.getId())
                        .status(StatusCode.IDLE)
                        .changedAt(LocalDateTime.of(2026, 5, 14, 8, 0))
                        .build(),
                MachineryStatusHistory.builder()
                        .machineryId(decommissionedTractor.getId())
                        .status(StatusCode.DECOMMISSIONED)
                        .changedAt(LocalDateTime.of(2026, 5, 10, 12, 0))
                        .build()));

        plannedWork = workRepository.save(Work.builder()
                .workType(WorkType.CULTIVATION)
                .fieldId(1L)
                .status(WorkStatus.PLANNED)
                .description("Cultivation work")
                .startDate(LocalDateTime.of(2026, 5, 14, 9, 0))
                .endDate(LocalDateTime.of(2026, 5, 14, 18, 0))
                .build());
        completedWork = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(2L)
                .status(WorkStatus.COMPLETED)
                .description("Harvesting work")
                .startDate(LocalDateTime.of(2026, 5, 13, 8, 0))
                .endDate(LocalDateTime.of(2026, 5, 13, 17, 0))
                .build());

        workMachineryRepository.saveAll(List.of(
                WorkMachinery.builder()
                        .workId(plannedWork.getId())
                        .machineryId(tractor.getId())
                        .build(),
                WorkMachinery.builder()
                        .workId(completedWork.getId())
                        .machineryId(combine.getId())
                        .build()
        ));
    }

    @Test
    void findAllByIdIn_shouldReturnMachineries() {
        List<Machinery> machineries = machineryRepository.findAllByIdIn(Set.of(tractor.getId(), combine.getId()));

        assertThat(machineries).hasSize(2);
        assertThat(machineries).extracting(Machinery::getId).containsExactlyInAnyOrder(tractor.getId(), combine.getId());
    }

    @Test
    void findAll_shouldReturnMachineriesWithPagination() {
        Slice<Machinery> result = machineryRepository.findAll(PageRequest.of(0, 2));

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.hasNext()).isTrue();
    }

    @Test
    void findAllByWorkIds_shouldReturnMachineriesAssignedToWorks() {
        List<Machinery> machineries = machineryRepository.findAllByWorkIds(Set.of(plannedWork.getId(), completedWork.getId()));

        assertThat(machineries).hasSize(2);
        assertThat(machineries).extracting(Machinery::getId).containsExactlyInAnyOrder(tractor.getId(), combine.getId());
    }

    @Test
    void findMachineryIdsByMachineryIdIn_shouldReturnExistingIds() {
        Set<Long> existingIds = machineryRepository.findMachineryIdsByMachineryIdIn(Set.of(tractor.getId(), combine.getId(), 11L));

        assertThat(existingIds).hasSize(2);
        assertThat(existingIds).containsExactlyInAnyOrder(tractor.getId(), combine.getId());
    }

    @Test
    void findConflictMachineryByStartDateAndEndDate_shouldReturnConflictingMachineries() {
        List<Machinery> conflictMachineries = machineryRepository.findConflictMachineryByStartDateAndEndDate(Set.of(tractor.getId(), combine.getId()),
                        List.of(WorkStatus.PLANNED), LocalDateTime.of(2026, 5, 14, 12, 0),
                        LocalDateTime.of(2026, 5, 14, 20, 0));

        assertThat(conflictMachineries).hasSize(1);
        assertThat(conflictMachineries).extracting(Machinery::getId).containsExactly(tractor.getId());
    }

    @Test
    void findMachineryByWorkId_shouldReturnMachineriesAssignedToWork() {
        List<Machinery> machineries = machineryRepository.findMachineryByWorkId(plannedWork.getId());

        assertThat(machineries).hasSize(1);
        assertThat(machineries).extracting(Machinery::getId).containsExactly(tractor.getId());
    }

    @Test
    void countMachineryByMachineryTypeAtStartDate_shouldReturnActiveMachineryCount() {
        List<MachineryTypeCount> result = machineryRepository.countMachineryByMachineryTypeAtStartDate(LocalDate.of(2026, 5, 31));

        MachineryTypeCount tractors = result.stream()
                .filter(r -> r.getMachineryType() == MachineryType.TRACTOR)
                .findFirst()
                .orElseThrow();

        MachineryTypeCount combines = result.stream()
                .filter(r -> r.getMachineryType() == MachineryType.COMBINE)
                .findFirst()
                .orElseThrow();

        assertThat(tractors.getCount()).isEqualTo(1);
        assertThat(combines.getCount()).isEqualTo(1);
    }

    @Test
    void countMachineryByMachineryTypeAndPurchaseDateBetween_shouldReturnPurchasedMachinery() {
        List<MachineryTypeCount> result = machineryRepository.countMachineryByMachineryTypeAndPurchaseDateBetween(LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 12, 31));

        MachineryTypeCount combines = result.stream()
                .filter(r -> r.getMachineryType() == MachineryType.COMBINE)
                .findFirst()
                .orElseThrow();

        assertThat(combines.getCount()).isEqualTo(1);
    }

    @Test
    void countDecommissionedMachineryByMachineryTypeAndBetweenDate_shouldReturnDecommissionedMachinery() {
        List<MachineryTypeCount> result = machineryRepository.countDecommissionedMachineryByMachineryTypeAndBetweenDate(LocalDate.of(2026, 5, 1),
                        LocalDate.of(2026, 5, 31));

        MachineryTypeCount tractors = result.stream()
                .filter(r -> r.getMachineryType() == MachineryType.TRACTOR)
                .findFirst()
                .orElseThrow();

        assertThat(tractors.getCount()).isEqualTo(1);
    }

    @Test
    void countMachineryByMachineryTypeAtEndDate_shouldReturnNonDecommissionedMachinery() {
        List<MachineryTypeCount> result = machineryRepository.countMachineryByMachineryTypeAtEndDate(LocalDate.of(2026, 5, 31));

        MachineryTypeCount tractors = result.stream()
                .filter(r -> r.getMachineryType() == MachineryType.TRACTOR)
                .findFirst()
                .orElseThrow();

        MachineryTypeCount combines = result.stream()
                .filter(r -> r.getMachineryType() == MachineryType.COMBINE)
                .findFirst()
                .orElseThrow();

        assertThat(tractors.getCount()).isEqualTo(1);
        assertThat(combines.getCount()).isEqualTo(1);
    }

}
