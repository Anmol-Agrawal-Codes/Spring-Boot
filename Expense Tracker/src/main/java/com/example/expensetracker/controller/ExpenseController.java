package com.example.expensetracker.controller;

import com.example.expensetracker.dto.ExpenseRequest;
import com.example.expensetracker.dto.ExpenseResponse;
import com.example.expensetracker.dto.ExpenseSummary;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Expense.PaymentMethod;
import com.example.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public void addExpense(@Valid @RequestBody ExpenseRequest expense) {
        expenseService.saveExpense(expense);
    }

    @GetMapping
    public List<ExpenseResponse> getAllExpensesByUser(@RequestParam Long userId) {
        return expenseService.getAllExpensesByUser(userId);
    }

    @GetMapping(params = {"month", "year"})
    public List<ExpenseResponse> getExpensesByUserAndMonthAndYear(@RequestParam Long userId, @RequestParam("month") int month, @RequestParam("year") int year) {
        return expenseService.getExpenseByUserAndMonthAndYear(userId, month, year);
    }

    @GetMapping(value = "/{id}", params = {"userId"})
    public ExpenseResponse getExpenseByUserAndId(@RequestParam Long userId, @PathVariable Long id){
        return expenseService.getExpenseByUserAndId(userId, id);
    }

    @GetMapping(params = {"userId", "category"})
    public List<ExpenseResponse> getAllExpensesByUserAndCategory(@RequestParam Long userId, @RequestParam Category category){
        return expenseService.getExpenseByUserAndCategory(userId, category);
    }

    @GetMapping(params = { "userId", "category", "month", "year" })
    public List<ExpenseResponse> getExpenseByUserAndCategoryAndMonthAndYear(@RequestParam Long userId, @RequestParam Category category, @RequestParam("month") int month, @RequestParam("year") int year){
        return expenseService.getExpenseByUserAndCategoryAndMonthAndYear(userId, category, month, year);
    }

    @GetMapping(value = "/report/monthly", params = "userId")
    public Map<Month, Double> getMonthlyExpenseByUser(@RequestParam Long userId){
        return expenseService.getMonthlyExpenseByUser(userId);
    }

    @GetMapping(value = "/report/categorySpends", params = "userId")
    public Map<Category, Double> getCategoryExpenseByUser(@RequestParam Long userId){
        return expenseService.getExpenseByUserAndCategory(userId);
    }

    @GetMapping(value = "/report/paymentMethod", params = "userId")
    public Map<PaymentMethod, Double> getPaymentMethodExpenseByUser(@RequestParam Long userId) {
        return expenseService.getExpenseByUserAndPaymentMethod(userId);
    }

    @GetMapping(value = "/report/summary", params = "userId")
    public ExpenseSummary getExpensesSummaryByUser(@RequestParam Long userId) {
        return expenseService.getExpenseSummaryByUser(userId);
    }

    @PatchMapping(value = "/{id}", params = "userId")
    @PutMapping(value = "/{id}", params = "userId")
    public void updateExpenseByUser(@RequestParam Long userId, @Valid @PathVariable Long id, @Valid @RequestBody ExpenseRequest updatedExpense) {
        expenseService.updateExpense(userId, id, updatedExpense);
    }

    @DeleteMapping(value = "/{id}", params = "userId")
    public void deleteExpenseByUserAndId(@PathVariable Long id, @RequestParam Long userId) {
        expenseService.deleteExpenseByUserAndId(userId, id);
    }
}
