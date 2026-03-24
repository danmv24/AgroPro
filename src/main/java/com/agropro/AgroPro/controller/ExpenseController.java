package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.ExpenseRequest;
import com.agropro.AgroPro.dto.request.ExpenseUpdateRequest;
import com.agropro.AgroPro.dto.response.ExpenseResponse;
import com.agropro.AgroPro.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/add")
    public ResponseEntity<Void> addExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
        expenseService.createExpense(expenseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> editExpense(@PathVariable(value = "id") Long id, @Valid @RequestBody ExpenseUpdateRequest expenseForm) {
        expenseService.updateExpense(id, expenseForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Slice<ExpenseResponse> getExpenses(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "15") int size) {
        return expenseService.getExpenses(page, size);
    }

}
