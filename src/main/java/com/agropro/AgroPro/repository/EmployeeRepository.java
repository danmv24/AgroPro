package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.Gender;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.projection.EmployeePositionCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends ListCrudRepository<Employee, Long> {

    List<Employee> findEmployeesByPosition(EmployeePosition position);

    Slice<Employee> findAll(Pageable pageable);

    @Query("SELECT id FROM employees WHERE id IN (:employeeIds)")
    Set<Long> findEmployeeIdsByIdIn(@Param("employeeIds") Set<Long> employeeIds);

    @Query("""
        SELECT DISTINCT we.employee_id FROM work_employees AS we
        INNER JOIN works AS w ON we.work_id = w.id
        WHERE we.employee_id IN(:employeeIds)
        AND w.status IN(:workStatuses)
        AND (w.end_date > :startDateOfWork AND w.start_date < :endDateOfWork)
    """)
    List<Long> findConflictEmployeeIdsByDateTime(@Param("employeeIds") Set<Long> employeeIds,
                                                 @Param("workStatuses") List<WorkStatus> workStatuses,
                                                 @Param("startDateOfWork") Timestamp startDateOfWork,
                                                 @Param("endDateOfWork") Timestamp endDateOfWork);

    @Query("""
        SELECT e.id, e.surname, e.name, e.patronymic, e.position, e.payment_type, e.salary, e.hire_date, e.gender FROM work_employees AS we
        INNER JOIN employees AS e ON e.id = we.employee_id
        WHERE we.work_id = :workId
    """)
    List<Employee> findEmployeesByFieldWorkId(@Param("workId") Long workId);

    @Query("""
        SELECT e.position, COUNT(*) AS count, SUM (case
            WHEN e.payment_type = 'FIXED' then e.salary
            WHEN e.payment_type = 'HOURLY' then e.salary * EXTRACT(EPOCH from (w.end_date - w.start_date)) / 3600
        END) as total_salary
        FROM employees AS e
        LEFT JOIN work_employees we ON we.employee_id = e.id
        LEFT JOIN works w ON w.id = we.work_id
            AND w.start_date >= :startDate
            AND w.end_date <= :endDate
        GROUP BY e.position
    """)
    List<EmployeePositionCount> findEmployeeCountByPosition(@Param("startDate") LocalDate startDate,
                                                            @Param("endDate") LocalDate endDate);

    long countEmployeesByGender(Gender gender);

}
