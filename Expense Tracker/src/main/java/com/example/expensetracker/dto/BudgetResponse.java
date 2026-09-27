package com.example.expensetracker.dto;

import com.example.expensetracker.entity.Category;

public record BudgetResponse(
        Long id,
        Category category,
        double monthlyLimit,
        int budgetMonth,
        int budgetYear
) {}