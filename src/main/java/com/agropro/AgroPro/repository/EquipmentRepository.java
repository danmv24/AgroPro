package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Equipment;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface EquipmentRepository extends ListCrudRepository<Equipment, Long> {

    List<Equipment> findAllByIdIn(Set<Long> equipmentIds);

    @Query("""
        SELECT e.id, e.inventory_number, e.equipment_type, e.current_status, e.purchase_date, e.equipment_name
        FROM equipment AS e
        INNER JOIN work_equipment AS we ON e.id = we.equipment_id
        WHERE we.work_id IN (:workIds)
    """)
    List<Equipment> findAllByWorkIds(@Param("workIds") Set<Long> workIds);

    List<Equipment> findEquipmentByCurrentStatus(StatusCode currentStatus);

    @Query("SELECT id FROM equipment WHERE id IN(:equipmentIds)")
    Set<Long> findEquipmentIdsByIdIn(@Param("equipmentIds") Set<Long> equipmentIds);

    @Query("""
        SELECT id, current_status, inventory_number, equipment_type, purchase_date, equipment_name FROM equipment
        WHERE id IN (:equipmentIds)
    """)
    List<Equipment> findStatusesByIdIn(@Param("equipmentIds") Set<Long> equipmentIds);

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

}
