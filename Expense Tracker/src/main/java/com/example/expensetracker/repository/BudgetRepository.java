package com.example.expensetracker.repository;

import com.example.expensetracker.entity.Budget;
import com.example.expensetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByCategory(Category category);
    List<Budget> findByBudgetMonth(int month);
    List<Budget> findByBudgetMonthAndBudgetYear(int month, int year);
    List<Budget> findByBudgetYear(int year);
    Budget findByCategoryAndBudgetMonthAndBudgetYear(Category category, int month, int year);
}
