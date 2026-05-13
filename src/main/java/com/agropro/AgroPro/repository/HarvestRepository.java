package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Harvest;
import com.agropro.AgroPro.projection.CropHarvest;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HarvestRepository extends ListCrudRepository<Harvest, Long> {

    @Query("""
        SELECT fp.crop_type, COALESCE(SUM(h.gross_harvest), 0) AS total_gross_harvest
        FROM harvests AS h
        INNER JOIN works AS w ON h.work_id = w.id
        INNER JOIN field_plantings AS fp ON w.field_id = fp.field_id
        WHERE w.start_date <= :endDate AND w.end_date >= :startDate
        GROUP BY fp.crop_type
    """)
    List<CropHarvest> findTotalHarvestByCropTypeAndDateRange(@Param("startDate") LocalDate startDate,
                                                             @Param("endDate") LocalDate endDate);

}
