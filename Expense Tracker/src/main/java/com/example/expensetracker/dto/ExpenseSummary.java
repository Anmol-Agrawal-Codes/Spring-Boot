package com.example.expensetracker.dto;

import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense;

import java.time.Month;
import java.util.Map;

public record ExpenseSummary(double totalExpense, int totalTransections, double avgExpenses,
                             double highestExpense, Map<Month, Double> monthlyExpenses,
                             Map<Category, Double> categoryExpenses,
                             Map<Expense.PaymentMethod, Double> paymentMethodExpenses) {
}
