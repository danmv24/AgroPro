package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.projection.WorkTypeHours;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkRepository extends ListCrudRepository<Work, Long> {

    @Query("""
        SELECT id, work_type, field_id, status, description, start_date, end_date
        FROM works
        WHERE status = :status AND start_date <= :now
    """)
    List<Work> findWorksToStart(@Param("status") WorkStatus status, @Param("now") LocalDateTime now);

    @Query("""
        SELECT id, work_type, field_id, status, description, start_date, end_date
        FROM works
        WHERE status = :status AND end_date < :now
    """)
    List<Work> findWorksToCompleted(@Param("status") WorkStatus status, @Param("now") LocalDateTime now);

    Slice<Work> findWorksByStatus(WorkStatus workStatus, Pageable pageable);

    @Query("""
        SELECT SUM(EXTRACT(EPOCH FROM (w.end_date - w.start_date)) / 3600 ) AS total_hours
        FROM works AS w
        INNER JOIN work_employees AS we ON w.id = we.work_id
        WHERE w.status = 'COMPLETED'
        AND w.start_date >= :startDate
        AND w.end_date <= :endDate
    """)
    Double sumWorkingHoursBetweenStartDateAndEndDate(@Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);


    @Query("""
        SELECT w.work_type, SUM(EXTRACT(EPOCH FROM (w.end_date - w.start_date)) / 3600 ) AS total_hours
        FROM works AS w
        INNER JOIN work_employees AS we ON w.id = we.work_id
        WHERE w.start_date >= :startDate AND w.end_date <= :endDate
        GROUP BY w.work_type
    """)
    List<WorkTypeHours> findWorkTypeWithTotalHours(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

}
