package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.service.EmployeeService;
import com.agropro.AgroPro.view.EmployeeView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<Void> addEmployee(@Valid @RequestBody EmployeeForm employeeForm) {
        employeeService.createEmployee(employeeForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public List<EmployeeView> getAllEmployees() {
        return employeeService.getEmployees();
    }

}
