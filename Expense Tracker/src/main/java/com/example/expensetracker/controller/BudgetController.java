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
@RequestMapping(value = "/api/budget")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping(params = "userId")
    public List<BudgetResponse> getBudgetByUser(@RequestParam Long userId) {
        return budgetService.getBudgetByUser(userId);
    }

    @PostMapping
    public void save(@Valid @RequestBody BudgetRequest budget) {
        budgetService.save(budget);
    }

    @GetMapping(params = {"userId", "category"})
    public List<BudgetResponse> getBudgetByUserAndCategory(@RequestParam Long userId, @RequestParam Category category) {
        return budgetService.getBudgetByUserAndCategory(userId, category);
    }

    @GetMapping(params = {"userId", "year"})
    public List<BudgetResponse> getBudgetByUserAndYear(@RequestParam Long userId, @RequestParam int year) {
        return budgetService.getBudgetByUserAndYear(userId, year);
    }

    @GetMapping(params = {"userId", "month"})
    public List<BudgetResponse> getBudgetByUserAndMonth(@RequestParam Long userId, @RequestParam int month) {
        return budgetService.getBudgetByUserAndMonth(userId, month);
    }

    @GetMapping(params = {"userId", "category", "year", "month"})
    public BudgetResponse getBudgetByUserAndCategoryAndYearAndMonth(@RequestParam Long userId, @RequestParam Category category, @RequestParam int month, @RequestParam int year) {
        return budgetService.getBudgetByUserAndCategoryAndBudgetMonthAndBudgetYear(userId, category, month, year);
    }

    @DeleteMapping(value = "/delete", params = {"userId", "id"})
    public void deleteBudgetByUserAndId(@RequestParam Long userId, @RequestParam Long id) {
        budgetService.deleteBudgetByUserAndId(userId, id);
    }

    @DeleteMapping(value = "/delete", params = {"userId", "category"})
    public void deleteBudgetByUserAndCategory(@RequestParam Long userId, @RequestParam Category category) {
        budgetService.deleteBudgetByUserAndCategory(userId, category);
    }

    @GetMapping(value = "/summary", params = {"userId", "category"})
    public BudgetSummaryResponse getBudgetSummaryByUserAndCategory(@RequestParam Long userId, @RequestParam Category category, @RequestParam int month, @RequestParam int year) {
        return  budgetService.getBudgetSummaryByUserAndCategory(userId, category, month, year);
    }
}
