package com.example.Smart_Investment_Planner.repo;

import com.example.Smart_Investment_Planner.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepo  extends JpaRepository<Budget,Integer> {
}
