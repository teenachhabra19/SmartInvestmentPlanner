package com.example.Smart_Investment_Planner.service;

import com.example.Smart_Investment_Planner.model.Budget;
import com.example.Smart_Investment_Planner.model.BudgetRequest;
import com.example.Smart_Investment_Planner.model.User;
import com.example.Smart_Investment_Planner.repo.BudgetRepo;
import com.example.Smart_Investment_Planner.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    BudgetRepo budgetRepo;
    public ResponseEntity<Budget> saveBudget(String name, BudgetRequest budgetRequest) {
        User user = userRepo.findByUsername(name);
        if(user==null){
            throw new RuntimeException("User not found");
        }
        try {
            Budget budget = new Budget();
            budget.setIncome(budgetRequest.getIncome());
            budget.setSavings(budgetRequest.getSavings());
            budget.setGoal(budgetRequest.getGoal());
            budget.setUser(user);
            budget.setRisk(budgetRequest.getRisk());
            Budget savedBudget = budgetRepo.save(budget);
            if (savedBudget.getId() == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(savedBudget, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
