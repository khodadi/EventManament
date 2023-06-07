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
@Table(name = "PSP" , schema = "NOTIF_USER")
public class PSP extends ABaseEntity {

    @Id
    @Column(name = "PSP_ID", nullable = true, insertable = true, updatable = true, length = 10)
    @GeneratedValue(generator = "PSP_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PSP_SEQ", allocationSize = 1, sequenceName = "PSP_SEQ",schema = "NOTIF_USER")
    private Long pspId;

    @Basic
    @Column(name = "NAME", nullable = true, insertable = true,updatable = true,length = 255)
    private String name;

    @Basic
    @Column(name = "FARSI_NAME", nullable = true, insertable = true,updatable = true,length = 255)
    private String farsiName;

    @Basic
    @Column(name = "CODE", nullable = false, insertable = true, updatable = true, length = 15)
    private Long code;

    @Basic
    @Column(name = "FOLDER_NAME", nullable = false, insertable = true, updatable = true, length = 15)
    private String folderName;

    @Basic
    @Column(name = "ABBREVIATION", nullable = true, insertable = true,updatable = true,length = 255)
    private String abbreviation;

    @Basic
    @Column(name = "SHORT_FOLDER_NAME", nullable = true, insertable = true,updatable = true,length = 255)
    private String shortFolderName;

    @Basic
    @Column(name = "USER_ID", nullable = false, insertable = true, updatable = true, length = 15)
    private Long UserId;

}
