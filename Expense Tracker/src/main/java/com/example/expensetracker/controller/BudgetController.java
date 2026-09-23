package com.example.expensetracker.controller;

import com.example.expensetracker.dto.BudgetResponse;
import com.example.expensetracker.entity.Budget;
import com.example.expensetracker.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public List<Budget> getBudgetByCategory(@RequestParam Budget.BudgetCategory category) {
        return budgetService.getBudgetByCategory(category);
    }

    @DeleteMapping(value = "/delete", params = "id")
    public void deleteBudgetById(@RequestParam Long id) {
        budgetService.deleteBudgetById(id);
    }

    @DeleteMapping(value = "/delete", params = "category")
    public void deleteBudgetByCategory(@RequestParam Budget.BudgetCategory category) {
        budgetService.deleteBudgetByCategory(category);
    }

    @GetMapping(value = "/summary", params = "category")
    public BudgetResponse getBudgetSummaryByCategory(@RequestParam Budget.BudgetCategory category) {
        return  budgetService.getBudgetSummaryByCategory(category);
    }

//    @GetMapping("/{id}")
//    public Budget getBudgetById(@PathVariable Long id) {
//        return budgetService.getBudgetById(id);
//    }



}
