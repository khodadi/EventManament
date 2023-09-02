package com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OccasionCostDto {
    private Long occasionCostId;
    private Long occasionCost;
    private Long userId;
    private Long occasionId;
    private String description;
}
