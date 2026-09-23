package com.example.expensetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Budget {

    public enum BudgetCategory {
        FOOD, TRANSPORT, SHOPPING, ENTERTAINMENT, BILLS, HEALTH, EDUCATION, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BudgetCategory category;

    @NotNull
    // @Positive // Feel free to uncomment this validation constraint
    private double monthlyLimit;

    @NotNull
//    @Enumerated(EnumType.STRING) // Saves month as "JANUARY", "FEBRUARY", etc.
    private int budgetMonth;

    @NotNull
    private int budgetYear;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BudgetCategory getCategory() { return category; }
    public void setCategory(BudgetCategory category) { this.category = category; }

    public double getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(double monthlyLimit) { this.monthlyLimit = monthlyLimit; }

    public int getBudgetMonth() { return budgetMonth; }
    public void setBudgetMonth(int month) { this.budgetMonth = month; }

    public int getBudgetYear() { return budgetYear; }
    public void setBudgetYear(int year) { this.budgetYear = year; }
}