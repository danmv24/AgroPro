package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.PaymentType;
import com.agropro.AgroPro.model.Employee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcNativeEmployeeRepository implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativeEmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee save(Employee employee) {
        String query = "INSERT INTO employees (surname, name, patronymic, position_id, payment_type, salary, hire_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, employee.getSurname());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getPatronymic());
            preparedStatement.setLong(4, employee.getPositionId());
            preparedStatement.setString(5, String.valueOf(employee.getPaymentType()));
            preparedStatement.setBigDecimal(6, employee.getSalary());
            preparedStatement.setObject(7, employee.getHireDate());
            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("employee_id")).longValue();
        employee.setEmployeeId(generatedId);

        return employee;
    }

    @Override
    public List<Employee> findAll() {
        String query = "SELECT employee_id, surname, name, patronymic, position_id, payment_type, salary FROM employees";
        return jdbcTemplate.query(query,
                (rs, rowNum) -> Employee.builder()
                        .employeeId(rs.getLong("employee_id"))
                        .surname(rs.getString("surname"))
                        .name(rs.getString("name"))
                        .patronymic(rs.getString("patronymic"))
                        .positionId(rs.getLong("position_id"))
                        .paymentType(PaymentType.valueOf(rs.getString("payment_type")))
                        .salary(rs.getBigDecimal("salary"))
                        .build()
                );
    }


}
