package com.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Activity",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends ABaseEntity{
    @Id
    @Column(name = "ACTIVITY_ID")
    @GeneratedValue(generator = "SEQ_ACTIVITY", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ACTIVITY", allocationSize = 1, sequenceName = "SEQ_ACTIVITY",schema = "ENV_DATA")
    private Long activityId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "NAME_FA")
    private String nameFa;
    @Column(name = "PIC_ID")
    private Long picId;

    @OneToOne
    @JoinColumn(name = "pic_Id",insertable = false ,updatable=false)
    private Pic pic;
}
