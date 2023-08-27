package com.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "EQUIPMENT",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment extends ABaseEntity{

    @Id
    @Column(name = "EQUIPMENT_ID")
    @GeneratedValue(generator = "SEQ_EQUIPMENT", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_EQUIPMENT", allocationSize = 1, sequenceName = "SEQ_EQUIPMENT",schema = "ENV_DATA")
    private Long equipmentId;
    @Column(name = "EQUIPMENT_NAME")
    private String equipmentName;
    @Column(name = "EQUIPMENT_NAME_FA")
    private String equipmentNameFa;
    @Column(name = "PARENT_EQUIPMENT_ID")
    private Long parentEquipmentId;
}
