package com.dao.entity;

import com.service.dto.OccasionCostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "OCCASION_COSR",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccasionCost extends ABaseEntity{
    @Id
    @Column(name = "OCCASION_COST_ID")
    @GeneratedValue(generator = "SEQ_OCCASION_COST", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_OCCASION_COST", allocationSize = 1, sequenceName = "SEQ_OCCASION_COST",schema = "ENV_DATA")
    private Long occasionCostId;
    @Column(name = "OCCASION_COST")
    private Long occasionCost;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "OCCASION_ID")
    private Long occasionId;
    @Column(name = "DESCRIPTION")
    private String description;

    public OccasionCost(OccasionCostDto dto){
        this(null,
                dto.getOccasionCost(),
                dto.getUserId(),
                dto.getOccasionId(),
                dto.getDescription());
    }
}
