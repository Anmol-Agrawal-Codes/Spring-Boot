package com.example.expensetracker.dto;

import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseRequest(
        @NotNull Long userId,
        @NotNull @Positive double amount,
        @NotBlank String description,
        @NotNull Category category,
        LocalDate expenseDate,
        @NotNull PaymentMethod paymentMethod
) {}
