package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends ListCrudRepository<Report, Long> {

    Slice<Report> findAll(Pageable pageable);

}
