package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.EmployeePosition;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.model.Employee;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findAll();

    List<Employee> findEmployeesByPosition(EmployeePosition position);

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

}
