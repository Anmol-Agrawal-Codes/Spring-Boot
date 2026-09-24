package com.example.expensetracker.controller;

import com.example.expensetracker.dto.BudgetSummaryResponse;
import com.example.expensetracker.entity.Budget;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public List<Budget> getBudget() {
        return budgetService.getBudget();
    }

    @PostMapping
    public void save(@RequestBody Budget budget) {
        budgetService.save(budget);
    }

    @GetMapping(params = "category")
    public List<Budget> getBudgetByCategory(@RequestParam Category category) {
        return budgetService.getBudgetByCategory(category);
    }

    @GetMapping(params = "year")
    public List<Budget> getBudgetByYear(@RequestParam int year) {
        return budgetService.getBudgetByYear(year);
    }

    @GetMapping(params = "month")
    public List<Budget> getBudgetByMonth(@RequestParam int month) {
        return budgetService.getBudgetByMonth(month);
    }

    @GetMapping(params = {"category", "year", "month"})
    public Budget getBudgetByCategoryAndYearAndMonth(@RequestParam Category category, @RequestParam int month, @RequestParam int year) {
        return budgetService.getBudgetByCategoryAndBudgetMonthAndBudgetYear(category, month, year);
    }

    @DeleteMapping(value = "/delete", params = "id")
    public void deleteBudgetById(@RequestParam Long id) {
        budgetService.deleteBudgetById(id);
    }

    @DeleteMapping(value = "/delete", params = "category")
    public void deleteBudgetByCategory(@RequestParam Category category) {
        budgetService.deleteBudgetByCategory(category);
    }

    @GetMapping(value = "/summary", params = "category")
    public BudgetSummaryResponse getBudgetSummaryByCategory(@RequestParam Category category) {
        return  budgetService.getBudgetSummaryByCategory(category);
    }

//    @GetMapping("/{id}")
//    public Budget getBudgetById(@PathVariable Long id) {
//        return budgetService.getBudgetById(id);
//    }



}
