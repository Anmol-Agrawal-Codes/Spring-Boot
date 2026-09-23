package com.example.expensetracker.controller;

import com.example.expensetracker.dto.ExpenseSummary;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.Expense.ExpenseCategory;
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

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
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

    @GetMapping(params = "category")
    public List<Expense> getAllExpensesByCategory(@RequestParam ExpenseCategory category){
        return expenseService.findByCategory(category);
    }

    @GetMapping("/report/monthly")
    public Map<Month, Double> getMonthlyExpense(){
        return expenseService.monthlyExpense();
    }

    @GetMapping("/report/categorySpends")
    public Map<ExpenseCategory, Double> getCategoryExpense(){
        return expenseService.expenseByCategory();
    }

    @GetMapping("/report/paymentMethod")
    public Map<PaymentMethod, Double> getPaymentMethodExpense() {
        return expenseService.expenseByPaymentMethod();
    }

    @GetMapping("/report/summary")
    public ExpenseSummary getExpensesSummary() {
        return expenseService.expenseSummary();
    }
}
