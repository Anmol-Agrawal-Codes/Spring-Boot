package com.example.expensetracker.dto;

import com.example.expensetracker.entity.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BudgetRequest(
        @NotNull Long userId,
        @NotNull Category category,
        @Positive @NotNull double monthlyLimit,
        @NotNull @Max(12) @Min(1) int budgetMonth,
        @NotNull @Min(2020) @Max(2100) int budgetYear
) {}
