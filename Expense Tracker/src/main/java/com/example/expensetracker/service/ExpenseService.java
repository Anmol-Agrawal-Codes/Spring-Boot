package com.example.expensetracker.service;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public void updateExpense(Long id, Expense updatedExpense) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        if (updatedExpense.getAmount() != 0.0) {
            expense.setAmount(updatedExpense.getAmount());
        }
        if (updatedExpense.getDescription() != null) {
            expense.setDescription(updatedExpense.getDescription());
        }
        if (updatedExpense.getCategory() != null) {
            expense.setCategory(updatedExpense.getCategory());
        }
        if (updatedExpense.getExpenseDate() != null) {
            expense.setExpenseDate(updatedExpense.getExpenseDate());
        }
        if (updatedExpense.getPaymentMethod() != null) {
            expense.setPaymentMethod(updatedExpense.getPaymentMethod());
        }
        saveExpense(expense);
    }

    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }
}
