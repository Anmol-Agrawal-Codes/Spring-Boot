package com.example.expensetracker.service;

import com.example.expensetracker.dto.BudgetResponse;
import com.example.expensetracker.entity.Budget;
import com.example.expensetracker.entity.Budget.BudgetCategory;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.repository.BudgetRepository;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Budget> getBudgetByCategory(BudgetCategory category) {
        return budgetRepository.findByCategory(category);
    }

    public void deleteBudgetByCategory(BudgetCategory category) {
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

    public BudgetResponse getBudgetSummaryByCategory(BudgetCategory category) {
        List<Budget> budgets = budgetRepository.findByCategory(category);
        List<Expense> expenses = expenseRepository.findByCategory(Expense.ExpenseCategory.valueOf(category.toString()));
        double budgetSum = 0;
        double spent = 0;
        double remaining = 0;
        double percentageUsed = 0;
        for (Budget budget : budgets) {
            budgetSum += budget.getMonthlyLimit();
        }
        for (Expense expense : expenses) {
            spent += expense.getAmount();
        }

        remaining = budgetSum - spent;
        percentageUsed = ((budgetSum - remaining) / budgetSum) * 100;

        return new BudgetResponse(category, budgetSum,
                spent, remaining, percentageUsed);
    }


//    public Budget getBudgetById(long id) {
//        return budgetRepository.findById(id);
//    }

//    public BudgetResponse getBudgetSummary(){
//
//    }
}

