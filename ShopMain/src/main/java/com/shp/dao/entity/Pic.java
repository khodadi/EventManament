package com.shp.dao.entity;

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
    @GeneratedValue(generator = "pic_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "pic_seq", allocationSize = 1, sequenceName = "pic_seq",schema = "env_shp")
    private Long picId;
    @Column(name = "PIC")
    private byte[] pic;
    @Column(name = "PIC_NAME")
    private  String picName;

    public Pic(byte[] pic, String picName){
        this(null,pic,picName);
    }
}
