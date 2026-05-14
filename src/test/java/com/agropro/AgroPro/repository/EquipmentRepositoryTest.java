package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.EquipmentType;
import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.model.EquipmentStatusHistory;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.model.WorkEquipment;
import com.agropro.AgroPro.projection.EquipmentTypeCount;
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

class EquipmentRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentStatusHistoryRepository equipmentStatusHistoryRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkEquipmentRepository workEquipmentRepository;

    private Equipment seeder;

    private Equipment sprayer;

    private Equipment oldSeeder;

    private Work plannedWork;

    private Work completedWork;

    @BeforeEach
    void setUp() {
        seeder = equipmentRepository.save(Equipment.builder()
                .equipmentName("Сеялка №1")
                .equipmentType(EquipmentType.SEEDER)
                .inventoryNumber(1)
                .currentStatus(StatusCode.IN_OPERATION)
                .purchaseDate(LocalDate.of(2024, 5, 10))
                .build());
        sprayer = equipmentRepository.save(Equipment.builder()
                .equipmentName("Опрыскиватель №1")
                .equipmentType(EquipmentType.SPRAYER)
                .inventoryNumber(2)
                .currentStatus(StatusCode.IDLE)
                .purchaseDate(LocalDate.of(2025, 3, 15))
                .build());
        oldSeeder = equipmentRepository.save(Equipment.builder()
                .equipmentName("Сеялка №2")
                .equipmentType(EquipmentType.SEEDER)
                .inventoryNumber(3)
                .currentStatus(StatusCode.DECOMMISSIONED)
                .purchaseDate(LocalDate.of(2020, 8, 1))
                .build());

        equipmentStatusHistoryRepository.saveAll(List.of(
                EquipmentStatusHistory.builder()
                        .equipmentId(seeder.getId())
                        .status(StatusCode.IN_OPERATION)
                        .changedAt(LocalDateTime.of(2026, 5, 14, 8, 0))
                        .build(),
                EquipmentStatusHistory.builder()
                        .equipmentId(sprayer.getId())
                        .status(StatusCode.IDLE)
                        .changedAt(LocalDateTime.of(2026, 5, 14, 8, 0))
                        .build(),
                EquipmentStatusHistory.builder()
                        .equipmentId(oldSeeder.getId())
                        .status(StatusCode.DECOMMISSIONED)
                        .changedAt(LocalDateTime.of(2026, 5, 10, 12, 0))
                        .build()
        ));

        plannedWork = workRepository.save(Work.builder()
                .workType(WorkType.SOWING)
                .fieldId(1L)
                .status(WorkStatus.PLANNED)
                .startDate(LocalDateTime.of(2026, 5, 14, 9, 0))
                .endDate(LocalDateTime.of(2026, 5, 14, 18, 0))
                .build());
        completedWork = workRepository.save(Work.builder()
                .workType(WorkType.CULTIVATION)
                .fieldId(2L)
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 5, 13, 8, 0))
                .endDate(LocalDateTime.of(2026, 5, 13, 17, 0))
                .build());

        workEquipmentRepository.saveAll(List.of(
                WorkEquipment.builder()
                        .workId(plannedWork.getId())
                        .equipmentId(seeder.getId())
                        .build(),

                WorkEquipment.builder()
                        .workId(completedWork.getId())
                        .equipmentId(sprayer.getId())
                        .build()
        ));
    }

    @Test
    void findAllByIdIn_shouldReturnEquipment() {
        List<Equipment> equipment = equipmentRepository.findAllByIdIn(Set.of(seeder.getId(), sprayer.getId()));

        assertThat(equipment).hasSize(2);
        assertThat(equipment).extracting(Equipment::getId).containsExactlyInAnyOrder(seeder.getId(), sprayer.getId());
    }

    @Test
    void findAll_shouldReturnEquipmentWithPagination() {
        Slice<Equipment> equipment = equipmentRepository.findAll(PageRequest.of(0, 2));

        assertThat(equipment.getContent()).hasSize(2);
        assertThat(equipment.hasNext()).isTrue();
    }

    @Test
    void findAllByWorkIdIn_shouldReturnEquipmentAssignedToWorks() {
        List<Equipment> equipment = equipmentRepository.findAllByWorkIdIn(Set.of(plannedWork.getId(), completedWork.getId()));

        assertThat(equipment).hasSize(2);
        assertThat(equipment).extracting(Equipment::getId).containsExactlyInAnyOrder(seeder.getId(), sprayer.getId());
    }

    @Test
    void findEquipmentIdsByIdIn_shouldReturnExistingIds() {
        Set<Long> equipment = equipmentRepository.findEquipmentIdsByIdIn(Set.of(seeder.getId(), sprayer.getId(), 11L));

        assertThat(equipment).hasSize(2);
        assertThat(equipment).containsExactlyInAnyOrder(seeder.getId(), sprayer.getId());
    }

    @Test
    void findConflictEquipmentByStartDateAndEndDate_shouldReturnConflictingEquipment() {
        List<Equipment> equipment = equipmentRepository.findConflictEquipmentByStartDateAndEndDate(Set.of(seeder.getId(), sprayer.getId()),
                        List.of(WorkStatus.PLANNED), LocalDateTime.of(2026, 5, 14, 12, 0),
                        LocalDateTime.of(2026, 5, 14, 20, 0));

        assertThat(equipment).hasSize(1);
        assertThat(equipment).extracting(Equipment::getId).containsExactly(seeder.getId());
    }

    @Test
    void findEquipmentByWorkId_shouldReturnEquipmentAssignedToWork() {
        List<Equipment> equipment = equipmentRepository.findEquipmentByWorkId(plannedWork.getId());

        assertThat(equipment).hasSize(1);
        assertThat(equipment).extracting(Equipment::getId).containsExactly(seeder.getId());
    }

    @Test
    void countEquipmentByEquipmentTypeAtStartDate_shouldReturnEquipmentCount() {
        List<EquipmentTypeCount> result = equipmentRepository.countEquipmentByEquipmentTypeAtStartDate(LocalDate.of(2026, 5, 31));

        EquipmentTypeCount seeders = result.stream()
                .filter(r -> r.getEquipmentType() == EquipmentType.SEEDER)
                .findFirst()
                .orElseThrow();

        EquipmentTypeCount sprayers = result.stream()
                .filter(r -> r.getEquipmentType() == EquipmentType.SPRAYER)
                .findFirst()
                .orElseThrow();

        assertThat(seeders.getCount()).isEqualTo(1);
        assertThat(sprayers.getCount()).isEqualTo(1);
    }

    @Test
    void countEquipmentByEquipmentTypeAndPurchaseDateBetween_shouldReturnPurchasedEquipment() {
        List<EquipmentTypeCount> result = equipmentRepository.countEquipmentByEquipmentTypeAndPurchaseDateBetween(
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31));

        EquipmentTypeCount sprayers = result.stream()
                .filter(r -> r.getEquipmentType() == EquipmentType.SPRAYER)
                .findFirst()
                .orElseThrow();

        assertThat(sprayers.getCount()).isEqualTo(1);
    }

    @Test
    void countDecommissionedEquipmentByEquipmentTypeAndBetweenDate_shouldReturnDecommissionedEquipment() {
        List<EquipmentTypeCount> result = equipmentRepository.countDecommissionedEquipmentByEquipmentTypeAndBetweenDate(
                        LocalDate.of(2026, 5, 1), LocalDate.of(2026, 5, 31));

        EquipmentTypeCount seeders = result.stream()
                .filter(r -> r.getEquipmentType() == EquipmentType.SEEDER)
                .findFirst()
                .orElseThrow();

        assertThat(seeders.getCount()).isEqualTo(1);
    }

    @Test
    void countEquipmentByEquipmentTypeAtEndDate_shouldReturnNonDecommissionedEquipment() {
        List<EquipmentTypeCount> result = equipmentRepository.countEquipmentByEquipmentTypeAtEndDate(LocalDate.of(2026, 5, 31));

        EquipmentTypeCount seeders = result.stream()
                .filter(r -> r.getEquipmentType() == EquipmentType.SEEDER)
                .findFirst()
                .orElseThrow();

        EquipmentTypeCount sprayers = result.stream()
                .filter(r -> r.getEquipmentType() == EquipmentType.SPRAYER)
                .findFirst()
                .orElseThrow();

        assertThat(seeders.getCount()).isEqualTo(1);
        assertThat(sprayers.getCount()).isEqualTo(1);
    }
}
