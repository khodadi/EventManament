package com.env.dao.entity;

import com.env.service.dto.OccasionCostDto;
import com.utility.InfraSecurityUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "OCCASION_COST",schema = "ENV_DATA")
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
    @OneToOne
    @JoinColumn(name = "OCCASION_ID",insertable = false ,updatable=false)
    private Occasion occasion;

    public OccasionCost(OccasionCostDto dto){
        this(null,
                dto.getOccasionCost(),
                dto.getUserId()== null? InfraSecurityUtils.getCurrentUser() :dto.getUserId(),
                dto.getOccasionId(),
                dto.getDescription(),
                null);
    }

    public void updateEnt(OccasionCostDto dto){
        this.setOccasionCost(Objects.nonNull(dto.getOccasionCost()) ? dto.getOccasionCost(): this.getOccasionCost());
        this.setUserId(Objects.nonNull(dto.getUserId()) ? dto.getUserId(): this.getUserId());
    }
}
