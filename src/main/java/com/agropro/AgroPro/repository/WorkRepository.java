package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.projection.CropStatistic;
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
        WHERE status = :status AND start_date <= :now AND end_date > :now
    """)
    List<Work> findWorksToStart(@Param("status") WorkStatus status, @Param("now") LocalDateTime now);

    @Query("""
        SELECT id, work_type, field_id, status, description, start_date, end_date
        FROM works
        WHERE status = :status AND end_date < :now
    """)
    List<Work> findWorksToCompleted(@Param("status") WorkStatus status, @Param("now") LocalDateTime now);

    Slice<Work> findSliceByStatusAndEndDateGreaterThanEqualAndEndDateLessThan(@Param("status") WorkStatus status,
                                                                              @Param("startDate") LocalDateTime startDate,
                                                                              @Param("endDate") LocalDateTime endDate,
                                                                              Pageable pageable);

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

    @Query("""
        SELECT fp.crop_type,
               COALESCE(SUM(wr.fuel_used), 0) AS fuel_used,
               COALESCE(SUM(wr.seeds_used), 0) AS seeds_used,
               COALESCE(SUM(wr.yield), 0) AS yield,
               COALESCE(SUM(wr.fertilizers_used), 0) AS fertilizers_used,
               SUM(e.salary * EXTRACT(EPOCH FROM (w.end_date - w.start_date)) / 3600) AS total_salary
        FROM works AS w
        INNER JOIN field_plantings AS fp ON fp.field_id = w.field_id
        LEFT JOIN work_employees we ON we.work_id = w.id
        LEFT JOIN employees e ON e.id = we.employee_id
        LEFT JOIN work_results wr ON wr.work_id = w.id
        WHERE w.start_date <= :endDate AND w.end_date >= :startDate
        GROUP BY fp.crop_type
    """)
    List<CropStatistic> findCropStatisticsByPeriod(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

//    @Query("""
//    SELECT fp.crop_type,
//           COALESCE(SUM(wr.fuel_used), 0) AS fuel_used,
//           COALESCE(SUM(wr.seeds_used), 0) AS seeds_used,
//           COALESCE(SUM(wr.harvest_amount), 0) AS yield,
//           COALESCE(SUM(wr.fertilizer_amount), 0) AS fertilizers_used,
//           SUM(e.salary * EXTRACT(EPOCH FROM (w.end_date - w.start_date)) / 3600) AS total_salary,
//           SUM(f.area) AS sown_area,
//           SUM(CASE WHEN fp.harvest_date IS NOT NULL AND fp.harvest_date BETWEEN :startDate AND :endDate
//                    THEN f.area
//                    ELSE 0
//               END) AS harvested_area
//    FROM works AS w
//    INNER JOIN field_plantings AS fp ON fp.field_id = w.field_id
//    INNER JOIN fields f ON f.id = fp.field_id
//    LEFT JOIN work_employees we ON we.work_id = w.id
//    LEFT JOIN employees e ON e.id = we.employee_id
//    LEFT JOIN work_results wr ON wr.work_id = w.id
//    WHERE w.start_date <= :endDate AND w.end_date >= :startDate AND fp.planting_date BETWEEN :startDate AND :endDate
//    GROUP BY fp.crop_type
//""")
//    List<CropStatistics> findCropStatisticsByPeriod(@Param("startDate") LocalDate startDate,
//                                                    @Param("endDate") LocalDate endDate);


}
