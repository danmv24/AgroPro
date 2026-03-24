package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.EmployeeRequest;
import com.agropro.AgroPro.dto.response.EmployeeResponse;
import com.agropro.AgroPro.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<Void> addEmployee(@Valid @RequestBody EmployeeRequest employeeForm) {
        employeeService.createEmployee(employeeForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public Slice<EmployeeResponse> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "15") int size) {
        return employeeService.getEmployees(page, size);
    }

}
