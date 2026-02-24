package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Expense;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends ListCrudRepository<Expense, Long> {



}
