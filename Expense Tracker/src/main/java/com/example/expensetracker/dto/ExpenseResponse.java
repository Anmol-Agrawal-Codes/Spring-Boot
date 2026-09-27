package com.example.expensetracker.dto;

import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense;

import java.time.LocalDate;

public record ExpenseResponse(
        Long id,
        double amount,
        String description,
        Category category,
        LocalDate expenseDate,
        Expense.PaymentMethod paymentMethod
) {}
