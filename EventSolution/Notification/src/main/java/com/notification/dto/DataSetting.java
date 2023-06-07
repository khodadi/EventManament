package com.notification.dto;

import com.infra.utils.InfraUtility;
import com.notification.entity.Setting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author m.shahrestanaki @createDate 12/7/2022
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataSetting {
    @NotNull(message = "settingdto_activesms_notnull:40200041")
    private boolean activeSms = false;
    @NotNull(message = "settingdto_activenotification_notnull:40200042")
    private boolean activeNotification = false;
    @NotNull(message = "settingdto_activeemail_notnull:40200043")
    private boolean activeEmail = false;
    @NotNull(message = "settingdto_activeAlarm_notnull:40200044")
    private boolean activeAlarm = false;
    private String ipAddressValidLoadMaster = "172.24.66.75";
    private String ipAddressValidSaveMaster = "172.24.66.75";
    private boolean activeLoadMaster = false;
    private boolean activeSaveMaster = false;

    public Setting convertDtoToEntity(){
        Setting setting = new Setting();
        setting.setUpdaterUserId(null);
        InfraUtility.giveCurrentUser().ifPresent(item->setting.setCreatorUserId(item.getUserId()));
        setting.setActiveSms(this.activeSms);
        setting.setActiveNotification(this.activeNotification);
        setting.setActiveEmail(this.activeEmail);
        setting.setActiveAlarm(this.activeAlarm);
        setting.setIpAddressValidLoadMaster(this.ipAddressValidLoadMaster);
        setting.setIpAddressValidSaveMaster(this.ipAddressValidSaveMaster);
        setting.setActiveLoadMaster(this.activeLoadMaster);
        setting.setActiveSaveMaster(this.activeSaveMaster);
        return setting;
    }

    public DataSetting(Setting setting) {
        this.activeSms = setting.isActiveSms();
        this.activeNotification = setting.isActiveNotification();
        this.activeEmail = setting.isActiveEmail();
        this.activeAlarm = setting.isActiveAlarm();
        this.ipAddressValidLoadMaster = setting.getIpAddressValidLoadMaster();
        this.ipAddressValidSaveMaster = setting.getIpAddressValidSaveMaster();
        this.activeLoadMaster = setting.isActiveLoadMaster();
        this.activeSaveMaster = setting.isActiveSaveMaster();
    }
}
