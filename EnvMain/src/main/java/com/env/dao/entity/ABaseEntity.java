package com.env.dao.entity;

import com.utility.DateUtils;
import com.utility.InfraSecurityUtils;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Setter
@Getter
@MappedSuperclass
public class ABaseEntity {

    public ABaseEntity(){
        this.creationDate = DateUtils.getCurrentDate();
        this.creatorUserId= InfraSecurityUtils.getCurrentUser();

    }

    @Column(name = "CREATION_DATE", nullable = true, insertable = true, updatable = true, precision = 0)
    private Timestamp creationDate;
    @Column(name = "CREATOR_USER_ID",  nullable = true, insertable = true, updatable = true, precision = 0)
    private Long creatorUserId;
    @Column(name = "LAST_UPDATE", nullable = true, insertable = true, updatable = true, precision = 0)
    private Timestamp lastUpdate;
    @Basic
    @Column(name = "UPDATER_USER_ID", nullable = true, insertable = false, updatable = false, precision = 0)
    private Long updaterUserId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "CREATOR_USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
//    private EnvUsers creatorUserByUserId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "UPDATER_USER_ID", referencedColumnName = "USER_ID",  insertable = false, updatable = false)
//    private EnvUsers updaterUserByUserId;
}
