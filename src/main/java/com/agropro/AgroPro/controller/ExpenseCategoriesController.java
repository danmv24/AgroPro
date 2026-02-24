package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.service.ExpenseCategoryService;
import com.agropro.AgroPro.view.ExpenseCategoryView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expense-categories")
public class ExpenseCategoriesController {

    private final ExpenseCategoryService categoryService;

    @GetMapping
    public List<ExpenseCategoryView> getCategories() {
        return categoryService.getCategories();
    }

}
