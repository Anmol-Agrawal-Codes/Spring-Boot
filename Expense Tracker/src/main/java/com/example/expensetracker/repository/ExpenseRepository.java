package com.example.expensetracker.repository;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.Expense.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(ExpenseCategory category);
    List<Expense> findByPaymentMethod(Expense.PaymentMethod method);
}
