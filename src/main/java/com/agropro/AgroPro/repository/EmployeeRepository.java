package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.Gender;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.projection.EmployeePositionStatistic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends ListCrudRepository<Employee, Long> {

    List<Employee> findEmployeesByPosition(EmployeePosition position);

    Slice<Employee> findAll(Pageable pageable);

    @Query("SELECT id FROM employees WHERE id IN (:employeeIds)")
    Set<Long> findEmployeeIdsByIdIn(@Param("employeeIds") Set<Long> employeeIds);

    @Query("""
        SELECT e.id, e.surname, e.name, e.patronymic, e.position, e.payment_type, e.salary, e.hire_date, e.gender
        FROM work_employees AS we
        INNER JOIN employees AS e ON e.id = we.employee_id
        WHERE we.work_id = :workId
    """)
    List<Employee> findEmployeesByWorkId(@Param("workId") Long workId);

    @Query("""
        SELECT DISTINCT e.id, surname, name, patronymic, position, payment_type, salary, hire_date, gender FROM work_employees AS we
        INNER JOIN works AS w ON we.work_id = w.id
        INNER JOIN employees AS e ON we.employee_id = e.id
        WHERE we.employee_id IN (:employeeIds)
        AND w.status IN (:workStatuses)
        AND (w.end_date > :startDateOfWork AND w.start_date < :endDateOfWork)
    """)
    List<Employee> findConflictEmployeesByDateTime(@Param("employeeIds") Set<Long> employeeIds,
                                                 @Param("workStatuses") List<WorkStatus> workStatuses,
                                                 @Param("startDateOfWork") LocalDateTime startDateOfWork,
                                                 @Param("endDateOfWork") LocalDateTime endDateOfWork);

    @Query("""
        WITH employee_salary AS (
            SELECT
                e.id,
                e.position,
                CASE
                    WHEN e.payment_type = 'FIXED' THEN e.salary
                    WHEN e.payment_type = 'HOURLY' THEN
                        COALESCE(
                            SUM(
                                e.salary *
                                EXTRACT(EPOCH FROM (w.end_date - w.start_date)) / 3600
                            ),
                            0
                        )
                END AS employee_salary
            FROM employees e
            LEFT JOIN work_employees we
                ON we.employee_id = e.id
            LEFT JOIN works w
                ON w.id = we.work_id
                AND w.start_date >= :startDate
                AND w.end_date <= :endDate
            GROUP BY
                e.id,
                e.position,
                e.payment_type,
                e.salary
        )
        SELECT
            position,
            COUNT(*) AS count,
            SUM(employee_salary) AS total_salary
        FROM employee_salary
        GROUP BY position;
    """)
    List<EmployeePositionStatistic> findEmployeeCountByPosition(@Param("startDate") LocalDate startDate,
                                                                @Param("endDate") LocalDate endDate);

    long countEmployeesByGender(Gender gender);

    @Query("""
        SELECT e.id, e.surname, e.name, e.patronymic, e.position, e.payment_type, e.salary, e.hire_date, e.gender
        FROM employees AS e
        LEFT JOIN users AS u ON e.id = u.employee_id
        WHERE u.employee_id IS NULL
    """)
    List<Employee> findEmployeesWithoutAccount();

}
