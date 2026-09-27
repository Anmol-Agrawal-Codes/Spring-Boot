package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseResponse;
import com.example.expensetracker.dto.ExpenseSummary;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense; // Your actual @Entity class
import com.example.expensetracker.entity.Expense.PaymentMethod;
import com.example.expensetracker.dto.ExpenseRequest; // Your clean validation Record DTO
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

    public List<ExpenseResponse> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        List<ExpenseResponse> expensesResponse = new ArrayList<>();
        for (Expense expense : expenses) {
            expensesResponse.add(new  ExpenseResponse(
                    expense.getId(),
                    expense.getAmount(),
                    expense.getDescription(),
                    expense.getCategory(),
                    expense.getExpenseDate(),
                    expense.getPaymentMethod()));
        }
        return expensesResponse;
    }

    public ExpenseResponse getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id: " + id));
        return new  ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getExpenseDate(),
                expense.getPaymentMethod()
        );
    }

    public void saveExpense(ExpenseRequest dto) {
        // Map record fields natively using accessor syntax: dto.amount() instead of getAmount()
        Expense expense = new Expense();
        expense.setAmount(dto.amount());
        expense.setDescription(dto.description());
        expense.setCategory(dto.category());
        expense.setExpenseDate(dto.expenseDate());
        expense.setPaymentMethod(dto.paymentMethod());

        expenseRepository.save(expense);
    }

    public void updateExpense(Long id, ExpenseRequest updatedExpenseDto) {
        // Fetch the mutable database Entity
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        // Use modern record field accessors to safely update the Entity fields
        if (updatedExpenseDto.amount() != 0.0) {
            expense.setAmount(updatedExpenseDto.amount());
        }
        if (updatedExpenseDto.description() != null) {
            expense.setDescription(updatedExpenseDto.description());
        }
        if (updatedExpenseDto.category() != null) {
            expense.setCategory(updatedExpenseDto.category());
        }
        if (updatedExpenseDto.expenseDate() != null) {
            expense.setExpenseDate(updatedExpenseDto.expenseDate());
        }
        if (updatedExpenseDto.paymentMethod() != null) {
            expense.setPaymentMethod(updatedExpenseDto.paymentMethod());
        }

        expenseRepository.save(expense);
    }

    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<ExpenseResponse> findByCategory(Category category) {
        List<Expense> expenses = expenseRepository.findByCategory(category);
        List<ExpenseResponse> expenseResponses = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseResponses.add(new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getExpenseDate(),
                expense.getPaymentMethod()
            ));
        }
        return expenseResponses;
    }

    public List<ExpenseResponse> findByPaymentMethod(PaymentMethod method) {
        List<Expense> expenses = expenseRepository.findByPaymentMethod(method);
        List<ExpenseResponse> expenseResponses = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseResponses.add(new ExpenseResponse(
                    expense.getId(),
                    expense.getAmount(),
                    expense.getDescription(),
                    expense.getCategory(),
                    expense.getExpenseDate(),
                    expense.getPaymentMethod()
            ));
        }
        return expenseResponses;
    }

    public double getTotalExpense() {
        double sum = 0.0;
        for (Expense expense : expenseRepository.findAll()) {
            sum += expense.getAmount(); // Reverted to Entity getter syntax
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
        int totalTransactions = 0;
        double highestExpense = 0;
        Map<Month, Double> monthlyExpense = new HashMap<>();
        Map<Category, Double> expenseByCategory = new HashMap<>();
        Map<PaymentMethod, Double> expenseByPaymentMethod = new HashMap<>();

        for (Expense expense : expenses) {
            totalExpense += expense.getAmount();
            totalTransactions++;
            highestExpense = Math.max(highestExpense, expense.getAmount());

            Month month = expense.getExpenseDate().getMonth();
            monthlyExpense.put(month, monthlyExpense.getOrDefault(month, 0.0) + expense.getAmount());
            Category category = expense.getCategory();
            expenseByCategory.put(category, expenseByCategory.getOrDefault(category, 0.0) + expense.getAmount());
            PaymentMethod paymentMethod = expense.getPaymentMethod();
            expenseByPaymentMethod.put(paymentMethod, expenseByPaymentMethod.getOrDefault(paymentMethod, 0.0) + expense.getAmount());
        }

        double avgExpense = totalTransactions > 0 ? (totalExpense / totalTransactions) : 0.0;

        return new ExpenseSummary(
                totalExpense,
                totalTransactions,
                avgExpense,
                highestExpense,
                monthlyExpense,
                expenseByCategory,
                expenseByPaymentMethod
        );
    }

    public List<ExpenseResponse> getExpenseByMonthAndYear(int month, int year) {
        List<Expense> expenses = expenseRepository.findAll();
        List<ExpenseResponse> expensesByMonthAndYear = new ArrayList<>();

        for (Expense expense : expenses) {
            if (expense.getExpenseDate().getYear() == year
                    && expense.getExpenseDate().getMonth().getValue() == month) {
                expensesByMonthAndYear.add(new  ExpenseResponse(
                        expense.getId(),
                        expense.getAmount(),
                        expense.getDescription(),
                        expense.getCategory(),
                        expense.getExpenseDate(),
                        expense.getPaymentMethod()
                ));
            }
        }
        return expensesByMonthAndYear;
    }

    public List<ExpenseResponse> getExpenseByCategoryAndMonthAndYear(Category category, int month, int year) {
        List<Expense> expenses = expenseRepository.findAll();
        List<ExpenseResponse> expensesByCategoryAndMonthAndYear = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getExpenseDate().getYear() == year
                && expense.getExpenseDate().getMonth().getValue() == month
                && expense.getCategory() == category) {
                expensesByCategoryAndMonthAndYear.add(new  ExpenseResponse(
                        expense.getId(),
                        expense.getAmount(),
                        expense.getDescription(),
                        expense.getCategory(),
                        expense.getExpenseDate(),
                        expense.getPaymentMethod()
                ));
            }
        }
        return expensesByCategoryAndMonthAndYear;
    }
}