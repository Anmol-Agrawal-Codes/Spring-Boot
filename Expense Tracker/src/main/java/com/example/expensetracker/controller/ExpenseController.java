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

    @GetMapping("/{id}")
    public ExpenseResponse getExpenseByUserAndId(@RequestParam Long userId, @PathVariable Long id){
        return expenseService.getExpenseByUserAndId(userId, id);
    }

    @GetMapping(params = "category")
    public List<ExpenseResponse> getAllExpensesByUserAndCategory(@RequestParam Long userId, @RequestParam Category category){
        return expenseService.findByUserAndCategory(userId, category);
    }

    @GetMapping(params = { "category", "month", "year" })
    public List<ExpenseResponse> getExpenseByUserAndCategoryAndMonthAndYear(@RequestParam Long userId, @RequestParam Category category, @RequestParam("month") int month, @RequestParam("year") int year){
        return expenseService.getExpenseByUserAndCategoryAndMonthAndYear(userId, category, month, year);
    }

    @GetMapping("/report/monthly")
    public Map<Month, Double> getMonthlyExpenseByUser(@RequestParam Long userId){
        return expenseService.getMonthlyExpenseByUser(userId);
    }

    @GetMapping("/report/categorySpends")
    public Map<Category, Double> getCategoryExpenseByUser(@RequestParam Long userId){
        return expenseService.getExpenseByUserAndCategory(userId);
    }

    @GetMapping("/report/paymentMethod")
    public Map<PaymentMethod, Double> getPaymentMethodExpenseByUser(@RequestParam Long userId) {
        return expenseService.getExpenseByUserAndPaymentMethod(userId);
    }

    @GetMapping("/report/summary")
    public ExpenseSummary getExpensesSummaryByUser(@RequestParam Long userId) {
        return expenseService.getExpenseSummaryByUser(userId);
    }

    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    public void updateExpenseByUser(@RequestParam Long userId, @Valid @PathVariable Long id, @Valid @RequestBody ExpenseRequest updatedExpense) {
        expenseService.updateExpense(userId, id, updatedExpense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpenseById(@PathVariable Long id){
        expenseService.deleteExpenseById(id);
    }
}
