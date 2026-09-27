package com.example.expensetracker.dto;

import com.example.expensetracker.entity.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BudgetRequest(
        @NotNull Category category,
        @Positive @NotNull double monthlyLimit,
        @NotNull int budgetMonth,
        @NotNull int budgetYear
        ) {}
