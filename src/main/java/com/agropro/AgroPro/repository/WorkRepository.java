package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.view.FieldWorkDetail;
import com.agropro.AgroPro.view.WorkBasicInfoView;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkRepository extends CrudRepository<Work, Long> {

    @Query("""
        SELECT w.id, w.work_type, w.status, w.start_date, w.end_date, f.field_number
        FROM works AS w
        INNER JOIN fields AS f ON f.id = w.field_id
        ORDER BY w.start_date, w.end_date
    """)
    List<WorkBasicInfoView> findAllBasicInfo();

    @Query("""
        SELECT w.id, w.work_type, w.status, w.start_date, w.end_date, f.field_number, w.description
        FROM works AS w
        INNER JOIN fields AS f ON f.id = w.field_id
        WHERE w.id = :workId
    """)
    Optional<FieldWorkDetail> findFieldWorkDetailByFieldWorkId(@Param("workId") Long workId);

    @Modifying
    @Query("UPDATE works SET status = :workStatus WHERE id = :workId")
    void updateStatusByFieldWorkId(@Param("workId") Long workId, @Param("workStatus") WorkStatus workStatus);

    @Query("SELECT status FROM works WHERE id = :workId")
    Optional<WorkStatus> findStatusByFieldWorkId(@Param("workId") Long workId);

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
}
