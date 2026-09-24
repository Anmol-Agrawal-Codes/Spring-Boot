package com.example.expensetracker.service;

import com.example.expensetracker.dto.BudgetSummaryResponse;
import com.example.expensetracker.entity.Budget;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.error.BudgetNotFoundException;
import com.example.expensetracker.repository.BudgetRepository;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    public void save(Budget budget) {
        budgetRepository.save(budget);
    }

    public List<Budget> getBudget() {
        return budgetRepository.findAll();
    }

    public List<Budget> getBudgetByCategory(Category category) {
        return budgetRepository.findByCategory(category);
    }

    public void deleteBudgetByCategory(Category category) {
        List<Budget> budgets = budgetRepository.findAll();
        for (Budget budget : budgets) {
            if (budget.getCategory() == category) {
                budgetRepository.delete(budget);
            }
        }
    }

    public void deleteBudgetById(Long id) {
        budgetRepository.deleteById(id);
    }

    public BudgetSummaryResponse getBudgetSummaryByCategory(Category category, int month, int year) {
        Budget budget = budgetRepository.findByCategoryAndBudgetMonthAndBudgetYear(category, month, year);
        if(budget == null) {
            throw new BudgetNotFoundException("Budget not found");
        }

        List<Expense> expenses = expenseRepository.findByCategoryAndExpenseDateBetween(category, LocalDate.of(year, month, 1), YearMonth.of(year, month).atEndOfMonth());
        if(budget.getMonthlyLimit() == 0){
            return new BudgetSummaryResponse(category, budget.getMonthlyLimit(), 0, 0, 0);
        }
        double budgetValue = budget.getMonthlyLimit();
        double spent = 0;
        double remaining = 0;
        double percentageUsed = 0;

        for (Expense expense : expenses) {
            spent += expense.getAmount();
        }

        remaining = budgetValue - spent;
        percentageUsed = ((budgetValue - remaining) / budgetValue) * 100;

        return new BudgetSummaryResponse(category, budgetValue,
                spent, remaining, percentageUsed);
    }

    public List<Budget> getBudgetByMonth(int month) {
        return budgetRepository.findByBudgetMonth(month);
    }

    public List<Budget> getBudgetByYear(int year) {
        return budgetRepository.findByBudgetYear(year);
    }

    public Budget getBudgetByCategoryAndBudgetMonthAndBudgetYear(Category category, int month, int year) {
        return budgetRepository.findByCategoryAndBudgetMonthAndBudgetYear(category, month, year);
    }
}

