package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Work;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
                                                                              @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
