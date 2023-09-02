package com.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * @Creator 8/12/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Entity
@Table(name = "Occasion_type",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccasionType extends ABaseEntity{
    @Id
    @Column(name = "OCCASION_TYPE_ID")
    @GeneratedValue(generator = "SEQ_OCCASION_TYPE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_OCCASION_TYPE", allocationSize = 1, sequenceName = "SEQ_OCCASION_TYPE",schema = "ENV_DATA")
    private Long occasionTypeId;
    @Column(name = "OCCASION_TYPE_NAME")
    private String occasionTypeName;
    @Column(name = "OCCASION_TYPE_NAME_FA")
    private String occasionTypeNameFa;
    @Column(name = "PIC_ID")
    private Long picId;
    @OneToOne
    @JoinColumn(name = "pic_Id",insertable = false ,updatable=false)
    private Pic pic;
    @OneToMany(mappedBy = "occasionTypeId")
    private Set<OccasionComponent> occasionComponents;
}
