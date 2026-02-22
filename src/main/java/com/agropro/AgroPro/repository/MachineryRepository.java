package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Machinery;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface MachineryRepository extends CrudRepository<Machinery, Long> {

    List<Machinery> findAll();

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

}
