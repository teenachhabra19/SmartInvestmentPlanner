package com.example.Smart_Investment_Planner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionDTO {
    private String name;
    private String type;
    private double investAmount;
    private double expectedReturn;
    private double returnRate;
}
