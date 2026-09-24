package com.example.expensetracker.repository;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(Category category);
    List<Expense> findByPaymentMethod(Expense.PaymentMethod method);
    List<Expense> findByExpenseDate(LocalDateTime expenseDate);
    List<Expense> findByCategoryAndExpenseDate(Category category, LocalDateTime expenseDate);
}
