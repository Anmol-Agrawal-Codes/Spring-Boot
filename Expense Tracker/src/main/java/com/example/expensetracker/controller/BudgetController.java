package com.example.expensetracker.controller;

import com.example.expensetracker.dto.BudgetRequest;
import com.example.expensetracker.dto.BudgetResponse;
import com.example.expensetracker.dto.BudgetSummaryResponse;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/budget", params = "userId")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public List<BudgetResponse> getBudgetByUser(@RequestParam Long userId) {
        return budgetService.getBudgetByUser(userId);
    }

    @PostMapping
    public void save(@Valid @RequestBody BudgetRequest budget) {
        budgetService.save(budget);
    }

    @GetMapping(params = "category")
    public List<BudgetResponse> getBudgetByUserAndCategory(@RequestParam Long userId, @RequestParam Category category) {
        return budgetService.getBudgetByUserAndCategory(userId, category);
    }

    @GetMapping(params = "year")
    public List<BudgetResponse> getBudgetByUserAndYear(@RequestParam Long userId, @RequestParam int year) {
        return budgetService.getBudgetByUserAndYear(userId, year);
    }

    @GetMapping(params = "month")
    public List<BudgetResponse> getBudgetByUserAndMonth(@RequestParam Long userId, @RequestParam int month) {
        return budgetService.getBudgetByUserAndMonth(userId, month);
    }

    @GetMapping(params = {"category", "year", "month"})
    public BudgetResponse getBudgetByUserAndCategoryAndYearAndMonth(@RequestParam Long userId, @RequestParam Category category, @RequestParam int month, @RequestParam int year) {
        return budgetService.getBudgetByUserAndCategoryAndBudgetMonthAndBudgetYear(userId, category, month, year);
    }

    @DeleteMapping(value = "/delete", params = "id")
    public void deleteBudgetById(@RequestParam Long id) {
        budgetService.deleteBudgetById(id);
    }

    @DeleteMapping(value = "/delete", params = "category")
    public void deleteBudgetByUserAndCategory(@RequestParam Long userId, @RequestParam Category category) {
        budgetService.deleteBudgetByUserAndCategory(userId, category);
    }

    @GetMapping(value = "/summary", params = "category")
    public BudgetSummaryResponse getBudgetSummaryByUserAndCategory(@RequestParam Long userId, @RequestParam User user, @RequestParam Category category, @RequestParam int month, @RequestParam int year) {
        return  budgetService.getBudgetSummaryByUserAndCategory(userId, category, month, year);
    }
}
