package com.example.expensetracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "budget",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_category_month_year",
                        columnNames = {"user_id", "category", "budget_month", "budget_year"}
                )
        }
)
@Getter
@Setter
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private Category category;
    private double monthlyLimit;
    private int budgetMonth;
    private int budgetYear;
}