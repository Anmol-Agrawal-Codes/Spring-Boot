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
                        columnNames = {"category", "month", "year"}
                )
        }
)
@Getter
@Setter
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Category category;
    private double monthlyLimit;
    private int budgetMonth;
    private int budgetYear;
}