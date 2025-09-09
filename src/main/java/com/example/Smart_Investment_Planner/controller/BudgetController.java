package com.example.Smart_Investment_Planner.controller;

import com.example.Smart_Investment_Planner.model.Budget;
import com.example.Smart_Investment_Planner.model.BudgetRequest;
import com.example.Smart_Investment_Planner.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
public class BudgetController {
    @Autowired
    BudgetService budgetService;
    @PostMapping("/save")
    public ResponseEntity<Budget> saveBudget(@RequestBody BudgetRequest budgetRequest, Principal principal){
        String username=principal.getName();
        System.out.println(username);
        return budgetService.saveBudget(principal.getName(),budgetRequest);
    }

}
