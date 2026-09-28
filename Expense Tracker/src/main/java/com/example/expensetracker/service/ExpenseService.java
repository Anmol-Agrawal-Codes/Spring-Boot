package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseResponse;
import com.example.expensetracker.dto.ExpenseSummary;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense; // Your actual @Entity class
import com.example.expensetracker.entity.Expense.PaymentMethod;
import com.example.expensetracker.dto.ExpenseRequest; // Your clean validation Record DTO
import com.example.expensetracker.entity.User;
import com.example.expensetracker.error.ExpenseNotFoundException;
import com.example.expensetracker.error.UserNotFoundException;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    public List<ExpenseResponse> getAllExpensesByUser(Long userId) {
        List<Expense> expenses = expenseRepository.findByUser(findUser(userId));
        List<ExpenseResponse> expensesResponse = new ArrayList<>();
        for (Expense expense : expenses) {
            expensesResponse.add(toResponse(expense));
        }
        return expensesResponse;
    }

    public void saveExpense(ExpenseRequest dto) {
        // Map record fields natively using accessor syntax: dto.amount() instead of getAmount()
        Expense expense = new Expense();
        expense.setUser(findUser(dto.userId()));
        expense.setAmount(dto.amount());
        expense.setDescription(dto.description());
        expense.setCategory(dto.category());
        expense.setExpenseDate(dto.expenseDate());
        expense.setPaymentMethod(dto.paymentMethod());

        expenseRepository.save(expense);
    }

    public void updateExpense(Long userId, Long id, ExpenseRequest updatedExpenseDto) {
        // Fetch the mutable database Entity
        Expense expense = expenseRepository.findByUserAndId(findUser(userId), id);
        if (expense == null) {
            throw new ExpenseNotFoundException("Expense not found");
        }
        expense.setAmount(updatedExpenseDto.amount());
        expense.setDescription(updatedExpenseDto.description());
        expense.setCategory(updatedExpenseDto.category());
        expense.setExpenseDate(updatedExpenseDto.expenseDate());
        expense.setPaymentMethod(updatedExpenseDto.paymentMethod());

        expenseRepository.save(expense);
    }

    public void deleteExpenseByUserAndId(Long userId, Long id) {
        Expense expense = expenseRepository.findByUserAndId(findUser(userId), id);
        if (expense == null)  {
            throw new ExpenseNotFoundException("Expense not found");
        }
        expenseRepository.delete(expense);
    }

    public ExpenseResponse getExpenseByUserAndId(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findByUserAndId(findUser(userId), expenseId);
        if (expense == null) {
            throw new ExpenseNotFoundException("Expense not found");
        }
        return toResponse(expense);
    }

    public List<ExpenseResponse> getExpenseByUserAndCategory(Long userId, Category category) {
        List<Expense> expenses = expenseRepository.findByUserAndCategory(findUser(userId), category);
        List<ExpenseResponse> expenseResponses = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseResponses.add(toResponse(expense));
        }
        return expenseResponses;
    }

    public List<ExpenseResponse> getExpenseByUserAndPaymentMethod(Long userId, PaymentMethod method) {
        List<Expense> expenses = expenseRepository.findByUserAndPaymentMethod(findUser(userId), method);
        List<ExpenseResponse> expenseResponses = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseResponses.add(toResponse(expense));
        }
        return expenseResponses;
    }

    public double getTotalExpenseByUser(Long userId) {
        double sum = 0.0;
        for (Expense expense : expenseRepository.findByUser(findUser(userId))) {
            sum += expense.getAmount(); // Reverted to Entity getter syntax
        }
        return sum;
    }

    public Map<Month, Double> getMonthlyExpenseByUser(Long userId) {
        Map<Month, Double> map = new HashMap<>();
        for (Expense expense : expenseRepository.findByUser(findUser(userId))) {
            Month month = expense.getExpenseDate().getMonth();
            map.put(month, map.getOrDefault(month, 0.0) + expense.getAmount());
        }
        return map;
    }

    public Map<Category, Double> getExpenseByUserAndCategory(Long userId) {
        Map<Category, Double> map = new HashMap<>();
        for (Expense expense : expenseRepository.findByUser(findUser(userId))) {
            Category category = expense.getCategory();
            map.put(category, map.getOrDefault(category, 0.0) + expense.getAmount());
        }
        return map;
    }

    public Map<PaymentMethod, Double> getExpenseByUserAndPaymentMethod(Long userId) {
        Map<PaymentMethod, Double> map = new HashMap<>();
        for (Expense expense : expenseRepository.findByUser(findUser(userId))) {
            PaymentMethod paymentMethod = expense.getPaymentMethod();
            map.put(paymentMethod, map.getOrDefault(paymentMethod, 0.0) + expense.getAmount());
        }
        return map;
    }

    public ExpenseSummary getExpenseSummaryByUser(Long userId) {
        List<Expense> expenses = expenseRepository.findByUser(findUser(userId));

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

    public List<ExpenseResponse> getExpenseByUserAndMonthAndYear(Long userId, int month, int year) {
        List<Expense> expenses = expenseRepository.findByUserAndExpenseDateBetween(
                findUser(userId),
                LocalDate.of(year, month, 1),
                YearMonth.of(year, month).atEndOfMonth()
        );
        List<ExpenseResponse> expensesByMonthAndYear = new ArrayList<>();

        for (Expense expense : expenses) {
            if (expense.getExpenseDate().getYear() == year
                    && expense.getExpenseDate().getMonth().getValue() == month) {
                expensesByMonthAndYear.add(toResponse(expense));
            }
        }
        return expensesByMonthAndYear;
    }

    public List<ExpenseResponse> getExpenseByUserAndCategoryAndMonthAndYear(Long userId, Category category, int month, int year) {
        List<Expense> expenses = expenseRepository.findByUser(findUser(userId));
        List<ExpenseResponse> expensesByCategoryAndMonthAndYear = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getExpenseDate().getYear() == year
                && expense.getExpenseDate().getMonth().getValue() == month
                && expense.getCategory() == category) {
                expensesByCategoryAndMonthAndYear.add(toResponse(expense));
            }
        }
        return expensesByCategoryAndMonthAndYear;
    }

    private User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    private ExpenseResponse toResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getUser().getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getExpenseDate(),
                expense.getPaymentMethod()
        );
    }
}