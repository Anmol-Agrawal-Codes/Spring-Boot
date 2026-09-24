package com.example.expensetracker.dto;

import com.example.expensetracker.entity.Category;

public record BudgetSummaryResponse(Category category, double budget,
                                    double spent, double remaining,
                                    double percentageUsed) {
}
