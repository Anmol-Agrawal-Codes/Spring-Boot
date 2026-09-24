package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseSummary;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense.PaymentMethod;
import com.example.expensetracker.error.ExpenseNotFoundException;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException("User not found with id: " + id));
    }
    
    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public void updateExpense(Long id, Expense updatedExpense) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
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

    public List<Expense> findByCategory(Category category) {
        return expenseRepository.findByCategory(category);
    }

    public List<Expense> findByPaymentMethod(Expense.PaymentMethod method) {
        return expenseRepository.findByPaymentMethod(method);
    }

    public double getTotalExpense() {
        double sum = 0.0;
        for (Expense expense : expenseRepository.findAll()) {
            sum += expense.getAmount();
        }
        return sum;
    }

    public Map<Month, Double> getMonthlyExpense() {
        Map<Month, Double> map = new HashMap<>();
        for (Expense expense : expenseRepository.findAll()) {
            Month month = expense.getExpenseDate().getMonth();
            map.put(month, map.getOrDefault(month, 0.0) + expense.getAmount());
        }
        return map;
    }

    public Map<Category, Double> getExpenseByCategory() {
        Map<Category, Double> map = new HashMap<>();
        for (Expense expense : expenseRepository.findAll()) {
            Category category = expense.getCategory();
            map.put(category, map.getOrDefault(category, 0.0) + expense.getAmount());
        }
        return map;
    }

    public Map<PaymentMethod, Double> getExpenseByPaymentMethod() {
        Map<PaymentMethod, Double> map = new HashMap<>();
        for (Expense expense : expenseRepository.findAll()) {
            PaymentMethod paymentMethod = expense.getPaymentMethod();
            map.put(paymentMethod, map.getOrDefault(paymentMethod, 0.0) + expense.getAmount());
        }
        return map;
    }

    public ExpenseSummary getExpenseSummary() {
        List<Expense> expenses = expenseRepository.findAll();

        double totalExpense = 0.0;
        int totalTransections = 0;
        double avgExpense = 0;
        double highestExpense = 0;
        Map<Month, Double> monthlyExpense = new HashMap<>();
        Map<Category, Double> expenseByCategory = new HashMap<>();
        Map<PaymentMethod, Double> expenseByPaymentMethod = new HashMap<>();

        for (Expense expense : expenses) {
            totalExpense +=  expense.getAmount();
            totalTransections++;
            highestExpense = Math.max(highestExpense, expense.getAmount());

            Month month = expense.getExpenseDate().getMonth();
            monthlyExpense.put(month, monthlyExpense.getOrDefault(month, 0.0) + expense.getAmount());
            Category category = expense.getCategory();
            expenseByCategory.put(category, expenseByCategory.getOrDefault(category, 0.0) + expense.getAmount());
            PaymentMethod paymentMethod = expense.getPaymentMethod();
            expenseByPaymentMethod.put(paymentMethod,  expenseByPaymentMethod.getOrDefault(paymentMethod, 0.0) + expense.getAmount());
        }
        avgExpense = totalExpense / totalTransections;

        return new ExpenseSummary(
                totalExpense,
                totalTransections,
                avgExpense,
                highestExpense,
                monthlyExpense,
                expenseByCategory,
                expenseByPaymentMethod
        );
    }

    public List<Expense> getExpenseByMonthAndYear(int month, int year) {
        List<Expense> expenses = expenseRepository.findAll();
        List<Expense> expensesByMonthAndYear = new ArrayList<>();

        for (Expense expense : expenses) {
            if(expense.getExpenseDate().getYear() == year
            && expense.getExpenseDate().getMonth().getValue() == month) {
                expensesByMonthAndYear.add(expense);
            }
        }
        return expensesByMonthAndYear;
    }

    public List<Expense> getExpenseByCategoryAndMonthAndYear(Category category, int month, int year) {
        List<Expense> expenses = expenseRepository.findAll();
        List<Expense> expensesByCategoryAndMonthAndYear = new ArrayList<>();
        for (Expense expense : expenses) {
            if(expense.getExpenseDate().getYear() == year
            && expense.getExpenseDate().getMonth().getValue() == month
            && expense.getCategory() == category) {
                expensesByCategoryAndMonthAndYear.add(expense);
            }
        }
        return expensesByCategoryAndMonthAndYear;
    }
}
