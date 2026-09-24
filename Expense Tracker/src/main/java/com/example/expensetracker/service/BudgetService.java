package com.example.expensetracker.service;

import com.example.expensetracker.dto.BudgetSummaryResponse;
import com.example.expensetracker.entity.Budget;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.repository.BudgetRepository;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public BudgetSummaryResponse getBudgetSummaryByCategory(Category category) {
        List<Budget> budgets = budgetRepository.findByCategory(category);
        List<Expense> expenses = expenseRepository.findByCategory(Category.valueOf(category.toString()));
        double budgetSum = 0;
        double spent = 0;
        double remaining = 0;
        double percentageUsed = 0;
        for (Budget budget : budgets) {
            budgetSum += budget.getMonthlyLimit();
        }
        if(budgetSum == 0){
            return new BudgetSummaryResponse(category, budgetSum, spent, remaining, percentageUsed);
        }
        
        for (Expense expense : expenses) {
            spent += expense.getAmount();
        }

        remaining = budgetSum - spent;
        percentageUsed = ((budgetSum - remaining) / budgetSum) * 100;

        return new BudgetSummaryResponse(category, budgetSum,
                spent, remaining, percentageUsed);
    }

    public List<Budget> getBudgetByMonth(int month) {
        return budgetRepository.findByBudgetMonth(month);
    }

    public List<Budget> getBudgetByYear(int year) {
        return budgetRepository.findByBudgetYear(year);
    }

    public Budget getBudgetByCategoryAndBudgetMonthAndBudgetYear(Category category, int month, int year) {
        return budgetRepository.findByCategoryAndBudgetYearAndBudgetMonth(category, month, year);
    }
}

