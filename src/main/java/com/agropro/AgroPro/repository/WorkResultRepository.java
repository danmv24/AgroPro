package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkResult;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WorkResultRepository extends ListCrudRepository<WorkResult, Long> {

    @Query("SELECT work_id FROM work_results WHERE work_id IN (:workIds)")
    Set<Long> findExistingResultWorkIds(@Param("workIds") Set<Long> workIds);

}
