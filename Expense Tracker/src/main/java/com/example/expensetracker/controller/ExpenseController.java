package com.example.expensetracker.controller;

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
    public void addExpense(@Valid @RequestBody Expense expense) {
        expenseService.saveExpense(expense);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping(params = {"month", "year"})
    public List<Expense> getExpensesByMonthAndYear(@RequestParam("month") int month, @RequestParam("year") int year) {
        return expenseService.getExpenseByMonthAndYear(month, year);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }

    @GetMapping(params = "category")
    public List<Expense> getAllExpensesByCategory(@RequestParam Category category){
        return expenseService.findByCategory(category);
    }

    @GetMapping(params = { "category", "month", "year" })
    public List<Expense> getExpenseByCategoryAndMonthAndYear(@RequestParam Category category, @RequestParam("month") int month, @RequestParam("year") int year){
        return expenseService.getExpenseByCategoryAndMonthAndYear(category, month, year);
    }

    @GetMapping("/report/monthly")
    public Map<Month, Double> getMonthlyExpense(){
        return expenseService.getMonthlyExpense();
    }

    @GetMapping("/report/categorySpends")
    public Map<Category, Double> getCategoryExpense(){
        return expenseService.getExpenseByCategory();
    }

    @GetMapping("/report/paymentMethod")
    public Map<PaymentMethod, Double> getPaymentMethodExpense() {
        return expenseService.getExpenseByPaymentMethod();
    }

    @GetMapping("/report/summary")
    public ExpenseSummary getExpensesSummary() {
        return expenseService.getExpenseSummary();
    }

    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    public void updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        expenseService.updateExpense(id, updatedExpense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpenseById(@PathVariable Long id){
        expenseService.deleteExpenseById(id);
    }
}
