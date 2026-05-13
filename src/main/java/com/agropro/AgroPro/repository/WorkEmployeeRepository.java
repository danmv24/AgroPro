package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkEmployee;
import com.agropro.AgroPro.projection.CropLaborCost;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkEmployeeRepository extends ListCrudRepository<WorkEmployee, Long> {

    @Query("""
        SELECT fp.crop_type, COALESCE(SUM(e.salary * EXTRACT(EPOCH FROM (w.end_date - w.start_date)) / 3600), 0) AS total_salary
        FROM work_employees AS we
        INNER JOIN works AS w ON we.work_id = w.id
        INNER JOIN employees AS e ON we.employee_id = e.id
        INNER JOIN field_plantings AS fp ON fp.field_id = w.field_id
        WHERE w.start_date <= :endDate AND w.end_date >= :startDate
        GROUP BY fp.crop_type
    """)
    List<CropLaborCost> findCropLaborCostsByDateRange(@Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

}
