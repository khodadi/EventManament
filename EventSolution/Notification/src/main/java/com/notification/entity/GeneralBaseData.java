package com.notification.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "TAB_SDW_BASETBL_MMS", schema = "NOTIF_USER")
public class GeneralBaseData {

    @Basic
    @Column(name = "BSTBL_ID")
    private Long bstblId;

    @Basic
    @Column(name = "CODE_MMS")
    private Long codeMms;

    @Basic
    @Column(name = "CODE_ISO")
    private Long codeIso;

    @Id
    @Column(name = "EDESC")
    private String edesc;

    @Basic
    @Column(name = "FDESC")
    private String fdesc;

    @Basic
    @Column(name = "ABBV")
    private String abbreviation;

    @Basic
    @Column(name = "BSTBL_DESC")
    private String bstblDesc;


}
