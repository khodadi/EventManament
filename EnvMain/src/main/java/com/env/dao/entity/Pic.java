package com.env.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PIC",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pic extends ABaseEntity{

    @Id
    @Column(name = "PIC_ID")
    @GeneratedValue(generator = "SEQ_PIC", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PIC", allocationSize = 1, sequenceName = "SEQ_PIC",schema = "ENV_DATA")
    private Long picId;
    @Column(name = "PIC")
    private byte[] pic;
    @Column(name = "PIC_NAME")
    private  String picName;
    @Column(name = "PUBLIC")
    private  boolean publicImage;


    public Pic(byte[] pic,String picName){
        this(null,pic,picName,true);
    }
}
