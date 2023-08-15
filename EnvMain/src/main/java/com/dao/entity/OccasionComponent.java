package com.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "OCCASION_COMPONENT",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccasionComponent extends ABaseEntity{
    @Id
    @Column(name = "occasion_Component_Id")
    @GeneratedValue(generator = "SEQ_OCCASION_COMPONENT", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_OCCASION_COMPONENT", allocationSize = 1, sequenceName = "SEQ_OCCASION_COMPONENT",schema = "ENV_DATA")
    private Long occasionComponentId;
    @Column(name = "OCCASION_TYPE_ID")
    private Long occasionTypeId;
    @Column(name = "COMPONENT_ID")
    private Long componentId;
    @OneToOne
    @JoinColumn(name = "COMPONENT_ID",insertable = false ,updatable=false)
    private Component component;
    @Column(name = "ORDER")
    private int order;
}
