package com.notification.entity;

import com.infra.entity.ABaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Province" , schema = "NOTIF_USER")
public class Province extends ABaseEntity {

    @Id
    @Column(name = "PROVINCE_ID", nullable = true, insertable = true, updatable = true, length = 10)
    @GeneratedValue(generator = "Province_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "Province_SEQ", allocationSize = 1, sequenceName = "Province_SEQ",schema = "NOTIF_USER")
    private Long provinceId;

    @Basic
    @Column(name = "NAME", nullable = true, insertable = true,updatable = true,length = 255)
    private String name;

    @Basic
    @Column(name = "FARSI_NAME", nullable = true, insertable = true,updatable = true,length = 255)
    private String farsiName;

    @Basic
    @Column(name = "ABBREVIATION", nullable = true, insertable = true,updatable = true,length = 255)
    private String abbreviation;

    @Basic
    @Column(name = "POSTAL_NAME", nullable = true, insertable = true,updatable = true,length = 255)
    private String postalName;

}
