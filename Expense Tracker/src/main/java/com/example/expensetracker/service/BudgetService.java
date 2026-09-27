package com.example.expensetracker.service;

import com.example.expensetracker.dto.BudgetRequest; // Your clean record DTO
import com.example.expensetracker.dto.BudgetSummaryResponse;
import com.example.expensetracker.dto.BudgetResponse;
import com.example.expensetracker.entity.Budget; // Database entity
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.error.BudgetNotFoundException;
import com.example.expensetracker.error.DuplicateBudgetException;
import com.example.expensetracker.repository.BudgetRepository;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    // Modified to receive the validated Record DTO
    public void save(BudgetRequest dto) {
        Budget budget = new Budget();
        // Map fields natively using record component accessors (no 'get' prefix)
        budget.setCategory(dto.category());
        budget.setMonthlyLimit(dto.monthlyLimit());
        budget.setBudgetMonth(dto.budgetMonth());
        budget.setBudgetYear(dto.budgetYear());

        if (budgetRepository.findByCategoryAndBudgetMonthAndBudgetYear(budget.getCategory(), budget.getBudgetMonth(), budget.getBudgetYear()) != null) {
            throw new DuplicateBudgetException("Budget already exists");
        }
        budgetRepository.save(budget);
    }

    public List<BudgetResponse> getBudget() {
        List<Budget> budgetList = budgetRepository.findAll();
        List<BudgetResponse> budgetResponseList = new ArrayList<>();
        for (Budget budget : budgetList) {
            budgetResponseList.add(new BudgetResponse(
                    budget.getId(),
                    budget.getCategory(),
                    budget.getMonthlyLimit(),
                    budget.getBudgetMonth(),
                    budget.getBudgetYear()
            ));
        }
        return budgetResponseList;
    }

    public List<BudgetResponse> getBudgetByCategory(Category category) {
        List<Budget> budgetList = budgetRepository.findByCategory(category);
        List<BudgetResponse> budgetResponseList = new ArrayList<>();
        for (Budget budget : budgetList) {
            budgetResponseList.add(new BudgetResponse(
                    budget.getId(),
                    budget.getCategory(),
                    budget.getMonthlyLimit(),
                    budget.getBudgetMonth(),
                    budget.getBudgetYear()
            ));
        }
        return budgetResponseList;
    }

    // Optimized: Delegate deletion directly to the database via Repository query
    @Transactional
    public void deleteBudgetByCategory(Category category) {
        budgetRepository.deleteByCategory(category);
    }

    public void deleteBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                        .orElseThrow(() -> new BudgetNotFoundException("No Budget is available with id: " + id));
        budgetRepository.deleteById(id);
    }

    public BudgetSummaryResponse getBudgetSummaryByCategory(Category category, int month, int year) {
        Budget budget = budgetRepository.findByCategoryAndBudgetMonthAndBudgetYear(category, month, year);
        if (budget == null) {
            throw new BudgetNotFoundException("Budget not found for the given category and date");
        }

        List<Expense> expenses = expenseRepository.findByCategoryAndExpenseDateBetween(
                category,
                LocalDate.of(year, month, 1),
                YearMonth.of(year, month).atEndOfMonth()
        );

        double budgetValue = budget.getMonthlyLimit();
        if (budgetValue == 0) {
            return new BudgetSummaryResponse(category, 0.0, 0.0, 0.0, 0.0);
        }

        double spent = 0;
        for (Expense expense : expenses) {
            spent += expense.getAmount();
        }

        double remaining = budgetValue - spent;
        // Simplified math formula: (spent / budgetValue) * 100
        double percentageUsed = (spent / budgetValue) * 100;

        return new BudgetSummaryResponse(category, budgetValue, spent, remaining, percentageUsed);
    }

    public List<BudgetResponse> getBudgetByMonth(int month) {
        List<Budget> budgetList = budgetRepository.findByBudgetMonth(month);
        List<BudgetResponse> budgetResponseList = new ArrayList<>();
        for (Budget budget : budgetList) {
            budgetResponseList.add(new BudgetResponse(
                    budget.getId(),
                    budget.getCategory(),
                    budget.getMonthlyLimit(),
                    budget.getBudgetMonth(),
                    budget.getBudgetYear()
            ));
        }
        return budgetResponseList;
    }

    public List<BudgetResponse> getBudgetByYear(int year) {
        List<Budget> budgetList = budgetRepository.findByBudgetYear(year);
        List<BudgetResponse> budgetResponseList = new ArrayList<>();
        for (Budget budget : budgetList) {
            budgetResponseList.add(new BudgetResponse(
                    budget.getId(),
                    budget.getCategory(),
                    budget.getMonthlyLimit(),
                    budget.getBudgetMonth(),
                    budget.getBudgetYear()
            ));
        }
        return budgetResponseList;
    }

    public BudgetResponse getBudgetByCategoryAndBudgetMonthAndBudgetYear(Category category, int month, int year) {
        Budget budget = budgetRepository.findByCategoryAndBudgetMonthAndBudgetYear(category, month, year);
        return new BudgetResponse(
                budget.getId(),
                budget.getCategory(),
                budget.getMonthlyLimit(),
                budget.getBudgetMonth(),
                budget.getBudgetYear()
        );
    }
}