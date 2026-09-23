package com.example.expensetracker.dto;

import com.example.expensetracker.entity.Budget;
import com.example.expensetracker.entity.Budget.BudgetCategory;

public record BudgetResponse(BudgetCategory category, double budget,
                             double spent, double remaining,
                             double percentageUsed) {
}
