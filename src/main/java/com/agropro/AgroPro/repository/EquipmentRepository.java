package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.projection.EquipmentTypeCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EquipmentRepository extends ListCrudRepository<Equipment, Long> {

    List<Equipment> findAllByIdIn(Set<Long> equipmentIds);

    Slice<Equipment> findAll(Pageable pageable);

    @Query("""
        SELECT e.id, e.inventory_number, e.equipment_type, e.current_status, e.purchase_date, e.equipment_name
        FROM equipment AS e
        INNER JOIN work_equipment AS we ON e.id = we.equipment_id
        WHERE we.work_id IN (:workIds)
    """)
    List<Equipment> findAllByWorkIdIn(@Param("workIds") Set<Long> workIds);

    @Query("SELECT id FROM equipment WHERE id IN(:equipmentIds)")
    Set<Long> findEquipmentIdsByIdIn(@Param("equipmentIds") Set<Long> equipmentIds);

    @Query("""
        SELECT DISTINCT e.id, e.equipment_name, e.equipment_type, e.inventory_number, e.purchase_date, e.current_status
        FROM work_equipment AS we
        INNER JOIN works AS w ON we.work_id = w.id
        INNER JOIN equipment AS e ON e.id = we.equipment_id
        WHERE we.equipment_id IN(:equipmentIds)
        AND w.status IN(:workStatuses)
        AND (w.end_date > :startDateOfWork AND w.start_date < :endDateOfWork)
    """)
    List<Equipment> findConflictEquipmentByStartDateAndEndDate(@Param("equipmentIds") Set<Long> equipmentIds,
                                                              @Param("workStatuses") List<WorkStatus> workStatuses,
                                                              @Param("startDateOfWork") Timestamp startDateOfWork,
                                                              @Param("endDateOfWork") Timestamp endDateOfWork);

    @Query("""
        SELECT e.id, e.equipment_name, e.equipment_type, e.current_status, e.purchase_date, e.inventory_number
        FROM work_equipment AS we
        INNER JOIN equipment AS e ON e.id = we.equipment_id
        WHERE we.work_id = :workId
    """)
    List<Equipment> findEquipmentByWorkId(@Param("workId") Long workId);

    @Query("""
        SELECT e.equipment_type, COUNT(*) AS count
        FROM equipment AS e
        INNER JOIN equipment_status_history AS esh ON esh.equipment_id = e.id
        WHERE esh.changed_at = (
             SELECT MAX(esh2.changed_at)
             FROM equipment_status_history AS esh2
             WHERE esh2.equipment_id = e.id
             AND esh2.changed_at::date <= :startDate
        )
        AND esh.status <> 'DECOMMISSIONED'
        AND e.purchase_date < :startDate
        GROUP BY e.equipment_type
    """)
    List<EquipmentTypeCount> countEquipmentByEquipmentTypeAtStartDate(@Param("startDate") LocalDate startDate);

    @Query("""
         SELECT e.equipment_type, COUNT(*) AS count
         FROM equipment AS e
         WHERE e.purchase_date BETWEEN :startDate AND :endDate
         GROUP BY e.equipment_type
    """)
    List<EquipmentTypeCount> countEquipmentByEquipmentTypeAndPurchaseDateBetween(@Param("startDate") LocalDate startDate,
                                                                                 @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT e.equipment_type, COUNT(*) AS count
        FROM equipment AS e
        INNER JOIN equipment_status_history AS esh ON esh.equipment_id = e.id
        WHERE esh.status = 'DECOMMISSIONED'
        AND esh.changed_at::date >= :startDate AND esh.changed_at::date <= :endDate
        GROUP BY e.equipment_type
    """)
    List<EquipmentTypeCount> countDecommissionedEquipmentByEquipmentTypeAndBetweenDate(@Param("startDate") LocalDate startDate,
                                                                                   @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT e.equipment_type, COUNT(*) AS count
        FROM equipment AS e
        INNER JOIN equipment_status_history AS esh ON esh.equipment_id = e.id
        WHERE esh.changed_at = (
             SELECT MAX(esh2.changed_at)
             FROM equipment_status_history AS esh2
             WHERE esh2.equipment_id = e.id
             AND esh2.changed_at::date <= :endDate
        )
        AND esh.status <> 'DECOMMISSIONED'
        AND e.purchase_date < :endDate
        GROUP BY e.equipment_type
    """)
    List<EquipmentTypeCount> countEquipmentByEquipmentTypeAtEndDate(@Param("endDate") LocalDate endDate);

}
