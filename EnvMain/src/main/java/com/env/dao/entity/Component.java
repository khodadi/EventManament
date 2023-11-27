package com.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Component",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Component extends ABaseEntity{
    @Id
    @Column(name = "COMPONENT_ID")
    @GeneratedValue(generator = "SEQ_COMPONENT", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_COMPONENT", allocationSize = 1, sequenceName = "SEQ_COMPONENT",schema = "ENV_DATA")
    private Long componentId;
    @Column(name = "COMPONENT_NAME")
    private String componentName;
    @Column(name = "COMPONENT_NAME_FA")
    private String componentNameFa;
    @Column(name = "PIC_ID")
    private Long picId;
}
