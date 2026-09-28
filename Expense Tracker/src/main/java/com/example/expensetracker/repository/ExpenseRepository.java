package com.example.expensetracker.repository;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    Expense findByUserAndId(User user, Long id);
    List<Expense> findByUserAndCategory(User user, Category category);
    List<Expense> findByUserAndPaymentMethod(User user, Expense.PaymentMethod method);
    List<Expense> findByUserAndCategoryAndExpenseDateBetween(User user, Category category, LocalDate startDate, LocalDate endDate);
}
