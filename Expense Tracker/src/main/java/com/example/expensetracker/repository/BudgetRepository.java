package com.example.expensetracker.repository;

import com.example.expensetracker.entity.Budget;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(User user);
    Budget findByUserAndId(User user, Long id);
    List<Budget> findByUserAndCategory(User user, Category category);
    List<Budget> findByUserAndBudgetMonth(User user, int month);
    List<Budget> findByUserAndBudgetYear(User user, int year);
    Budget findByUserAndCategoryAndBudgetMonthAndBudgetYear(User user, Category category, int month, int year);
    void  deleteByUserAndCategory(User user, Category category);
}
