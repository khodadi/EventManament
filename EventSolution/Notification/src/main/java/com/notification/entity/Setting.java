package com.notification.entity;

import com.infra.entity.ABaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author m.shahrestanaki @createDate 12/7/2022
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SETTING", schema = "NOTIF_USER")
public class Setting extends ABaseEntity {
    @Id
    @Column(name = "SETTING_ID", nullable = false, updatable = false)
    @GeneratedValue(generator = "SETTING_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SETTING_SEQ", allocationSize = 1, sequenceName = "SETTING_SEQ", schema = "NOTIF_USER")
    private Long SettingId;

    @Basic
    @Column(name = "ACTIVE_SMS", nullable = false)
    private boolean activeSms;
    @Basic
    @Column(name = "ACTIVE_NOTIFICATION", nullable = false)
    private boolean activeNotification;
    @Basic
    @Column(name = "ACTIVE_EMAIL", nullable = false)
    private boolean activeEmail;
    @Basic
    @Column(name = "ACTIVE_ALARM", nullable = false)
    private boolean activeAlarm;
    @Basic
    @Column(name = "ACTIVE_SETTING", nullable = false)
    private boolean activeSetting;
    @Basic
    @Column(name = "IP_ADDRESS_VALID_LOAD_MASTER", nullable = false)
    private String ipAddressValidLoadMaster;
    @Basic
    @Column(name = "IP_ADDRESS_VALID_SAVE_MASTER",nullable = false)
    private String ipAddressValidSaveMaster;
    @Basic
    @Column(name = "ACTIVE_LOAD_MASTER",nullable = false)
    private boolean activeLoadMaster;
    @Basic
    @Column(name = "ACTIVE_SAVE_MASTER",nullable = false)
    private boolean activeSaveMaster;


}
