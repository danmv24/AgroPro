package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkMaterialUsage;
import com.agropro.AgroPro.projection.CropMaterialCost;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface WorkMaterialUsageRepository extends ListCrudRepository<WorkMaterialUsage, Long> {

    @Query("""
        SELECT fp.crop_type, m.material_type, COALESCE(SUM(wmu.total_cost), 0) AS total_cost
        FROM work_material_usage AS wmu
        INNER JOIN materials AS m ON wmu.material_id = m.id
        INNER JOIN works AS w ON wmu.work_id = w.id
        INNER JOIN field_plantings AS fp ON fp.field_id = w.field_id
        WHERE w.start_date <= :endDate AND w.end_date >= :startDate
        AND (fp.harvest_date IS NULL OR fp.harvest_date >= w.end_date)
        GROUP BY fp.crop_type, m.material_type
    """)
    List<CropMaterialCost> findCostsByCropTypeAndMaterialTypeBetweenDateRange(@Param("startDate") LocalDate startDate,
                                                                              @Param("endDate") LocalDate endDate);

}
