package com.example.expensetracker.service;

import com.example.expensetracker.dto.BudgetRequest; // Your clean record DTO
import com.example.expensetracker.dto.BudgetSummaryResponse;
import com.example.expensetracker.dto.BudgetResponse;
import com.example.expensetracker.entity.Budget; // Database entity
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.error.BudgetNotFoundException;
import com.example.expensetracker.error.DuplicateBudgetException;
import com.example.expensetracker.error.UserNotFoundException;
import com.example.expensetracker.repository.BudgetRepository;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    // Modified to receive the validated Record DTO
    public void save(BudgetRequest dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        Budget budget = new Budget();
        // Map fields natively using record component accessors (no 'get' prefix)
        budget.setUser(user);
        budget.setCategory(dto.category());
        budget.setMonthlyLimit(dto.monthlyLimit());
        budget.setBudgetMonth(dto.budgetMonth());
        budget.setBudgetYear(dto.budgetYear());

        if (budgetRepository.findByUserAndCategoryAndBudgetMonthAndBudgetYear(budget.getUser(), budget.getCategory(), budget.getBudgetMonth(), budget.getBudgetYear()) != null) {
            throw new DuplicateBudgetException("Budget already exists");
        }
        budgetRepository.save(budget);
    }

    public List<BudgetResponse> getBudgetByUser(Long userId) {
        List<Budget> budgetList = budgetRepository.findByUser(findUser(userId));
        List<BudgetResponse> budgetResponseList = new ArrayList<>();
        for (Budget budget : budgetList) {
            budgetResponseList.add(toResponse(budget));
        }
        return budgetResponseList;
    }

    public List<BudgetResponse> getBudgetByUserAndCategory(Long userId, Category category) {
        List<Budget> budgetList = budgetRepository.findByUserAndCategory(findUser(userId), category);
        List<BudgetResponse> budgetResponseList = new ArrayList<>();
        for (Budget budget : budgetList) {
            budgetResponseList.add(toResponse(budget));
        }
        return budgetResponseList;
    }

    // Optimized: Delegate deletion directly to the database via Repository query
    @Transactional
    public void deleteBudgetByUserAndCategory(Long userId, Category category) {
        budgetRepository.deleteByUserAndCategory(findUser(userId), category);
    }

    public void deleteBudgetByUserAndId(Long userId, Long id) {
        Budget budget = budgetRepository.findByUserAndId(findUser(userId), id);
        budgetRepository.delete(budget);
    }

    public BudgetSummaryResponse getBudgetSummaryByUserAndCategory(Long userId, Category category, int month, int year) {
        Budget budget = budgetRepository.findByUserAndCategoryAndBudgetMonthAndBudgetYear(findUser(userId), category, month, year);
        if (budget == null) {
            throw new BudgetNotFoundException("Budget not found for the given category and date");
        }

        List<Expense> expenses = expenseRepository.findByUserAndCategoryAndExpenseDateBetween(
                findUser(userId),
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

    public List<BudgetResponse> getBudgetByUserAndMonth(Long userId, int month) {
        List<Budget> budgetList = budgetRepository.findByUserAndBudgetMonth(findUser(userId), month);
        List<BudgetResponse> budgetResponseList = new ArrayList<>();
        for (Budget budget : budgetList) {
            budgetResponseList.add(toResponse(budget));
        }
        return budgetResponseList;
    }

    public List<BudgetResponse> getBudgetByUserAndYear(Long userId, int year) {
        List<Budget> budgetList = budgetRepository.findByUserAndBudgetYear(findUser(userId), year);
        List<BudgetResponse> budgetResponseList = new ArrayList<>();
        for (Budget budget : budgetList) {
            budgetResponseList.add(toResponse(budget));
        }
        return budgetResponseList;
    }

    public BudgetResponse getBudgetByUserAndCategoryAndBudgetMonthAndBudgetYear(Long userId, Category category, int month, int year) {
        Budget budget = budgetRepository.findByUserAndCategoryAndBudgetMonthAndBudgetYear(findUser(userId), category, month, year);
        if (budget == null) {
            throw new BudgetNotFoundException("Budget not found for the given category and date");
        }
        return toResponse(budget);
    }

    private BudgetResponse toResponse(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getUser().getId(),
                budget.getCategory(),
                budget.getMonthlyLimit(),
                budget.getBudgetMonth(),
                budget.getBudgetYear()
        );
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }
}