package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.view.EmployeeBasicInfoView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository {

    void save(Employee employee);

    List<Employee> findAll();

    List<EmployeeBasicInfoView> findEmployeesWherePaymentTypeIsHourly();

    boolean existsByEmployeeId(Long employeeId);

    List<EmployeeBasicInfoView> findEmployeesWherePositionIsMechanizator();

    Set<Long> findExistingEmployeesByIds(Set<Long> employeeIds);

    List<Long> findConflictEmployeeIdsByDateTime(Set<Long> employeeIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);
}
