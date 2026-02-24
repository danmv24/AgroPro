package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.ExpenseCategory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryRepository extends ListCrudRepository<ExpenseCategory, Long> {



}
