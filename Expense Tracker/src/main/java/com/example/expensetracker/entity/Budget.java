package com.example.expensetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(
        name = "budget",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_category_month_year",
                        columnNames = {"category", "month", "year"}
                )
        }
)
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    @NotNull
    @Positive
    private double monthlyLimit;

    @NotNull
//    @Enumerated(EnumType.STRING) // Saves month as "JANUARY", "FEBRUARY", etc.
    private int budgetMonth;

    @NotNull
    private int budgetYear;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public double getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(double monthlyLimit) { this.monthlyLimit = monthlyLimit; }

    public int getBudgetMonth() { return budgetMonth; }
    public void setBudgetMonth(int month) { this.budgetMonth = month; }

    public int getBudgetYear() { return budgetYear; }
    public void setBudgetYear(int year) { this.budgetYear = year; }
}