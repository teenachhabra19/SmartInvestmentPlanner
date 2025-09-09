package com.example.Smart_Investment_Planner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {
    private double income;
    private double savings;
    private String goal;
    private String risk;
}
