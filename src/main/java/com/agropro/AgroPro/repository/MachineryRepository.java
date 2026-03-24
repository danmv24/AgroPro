package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.projection.MachineryTypeCount;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface MachineryRepository extends ListCrudRepository<Machinery, Long> {

    List<Machinery> findAllByIdIn(Set<Long> machineryIds);

    @Query("""
        SELECT m.id, m.machinery_type, m.machinery_name, m.inventory_number, m.license_plate, m.current_status, m.purchase_date
        FROM machineries AS m
        INNER JOIN work_machineries AS wm ON m.id = wm.machinery_id
        WHERE wm.work_id IN (:workIds)
    """)
    List<Machinery> findAllByWorkIds(@Param("workIds") Set<Long> workIds);

    List<Machinery> findMachineryByCurrentStatus(StatusCode currentStatus);

    @Query("SELECT id FROM machineries WHERE id IN(:machineryIds)")
    Set<Long> findMachineryIdsByMachineryIdIn(@Param("machineryIds") Set<Long> machineryIds);

//    @Query("""
//        SELECT id, current_status, machinery_name, machinery_type, inventory_number, license_plate, purchase_date
//        FROM machineries WHERE id IN (:machineryIds)
//    """)
    List<Machinery> findMachineryByIdIn(Set<Long> machineryIds);

    @Query("""
        SELECT DISTINCT m.id, m.machinery_name, m.machinery_type, m.inventory_number, m.license_plate, m.current_status, m.purchase_date
        FROM work_machineries AS wm
        INNER JOIN works AS w ON wm.work_id = w.id
        INNER JOIN machineries AS m ON wm.machinery_id = m.id
        WHERE wm.machinery_id IN(:machineryIds)
        AND w.status IN(:fieldWorkStatuses)
        AND (w.end_date > :startDateOfWork AND w.start_date < :endDateOfWork)
    """)
    List<Machinery> findConflictMachineryByStartDateAndEndDate(@Param("machineryIds") Set<Long> machineryIds,
                                                  @Param("fieldWorkStatuses") List<WorkStatus> fieldWorkStatuses,
                                                  @Param("startDateOfWork") Timestamp startDateOfWork,
                                                  @Param("endDateOfWork") Timestamp endDateOfWork);

    @Query("""
        SELECT m.id, m.machinery_name, m.machinery_type, m.license_plate, m.purchase_date, m.inventory_number, m.current_status
        FROM work_machineries AS wm
        INNER JOIN machineries AS m ON m.id = wm.machinery_id
        WHERE wm.work_id = :workId
    """)
    List<Machinery> findMachineryByWorkId(@Param("workId") Long workId);

    @Query("""
        SELECT m.machinery_type, COUNT(*) AS count
        FROM machineries AS m
        INNER JOIN machinery_status_history AS msh ON msh.machinery_id = m.id
        WHERE msh.changed_at = (
             SELECT MAX(msh2.changed_at)
             FROM machinery_status_history AS msh2
             WHERE msh2.machinery_id = m.id
             AND msh2.changed_at::date <= :startDate
        )
        AND msh.status <> 'DECOMMISSIONED'
        AND m.purchase_date < :startDate
        GROUP BY m.machinery_type
    """)
    List<MachineryTypeCount> countMachineryByMachineryTypeAtStartDate(@Param("startDate") LocalDate startDate);

    @Query("""
         SELECT m.machinery_type, COUNT(*) AS count
         FROM machineries AS m
         WHERE m.purchase_date BETWEEN :startDate AND :endDate
         GROUP BY m.machinery_type
    """)
    List<MachineryTypeCount> countMachineryByMachineryTypeAndPurchaseDateBetween(@Param("startDate") LocalDate startDate,
                                                                                 @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT m.machinery_type, COUNT(*) AS count
        FROM machineries AS m
        INNER JOIN machinery_status_history AS msh ON msh.machinery_id = m.id
        WHERE msh.status = 'DECOMMISSIONED'
        AND msh.changed_at::date BETWEEN :startDate AND :endDate
        GROUP BY m.machinery_type
    """)
    List<MachineryTypeCount> countDecommissionedMachineryByMachineryTypeAndBetweenDate(@Param("startDate") LocalDate startDate,
                                                                                       @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT m.machinery_type, COUNT(*) AS count
        FROM machineries AS m
        INNER JOIN machinery_status_history AS msh ON msh.machinery_id = m.id
        WHERE msh.changed_at = (
             SELECT MAX(msh2.changed_at)
             FROM machinery_status_history AS msh2
             WHERE msh2.machinery_id = m.id
             AND msh2.changed_at::date <= :endDate
        )
        AND msh.status <> 'DECOMMISSIONED'
        AND m.purchase_date < :endDate
        GROUP BY m.machinery_type
    """)
    List<MachineryTypeCount> countMachineryByMachineryTypeAtEndDate(@Param("endDate") LocalDate endDate);

}
