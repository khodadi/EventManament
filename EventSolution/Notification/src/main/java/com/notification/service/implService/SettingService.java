package com.notification.service.implService;

import com.infra.dto.Output;
import com.infra.exception.CodeException;
import com.infra.utils.DateUtils;
import com.infra.utils.InfraUtility;
import com.infra.utils.MessageUtils;
import com.notification.dto.DataSetting;
import com.notification.entity.Setting;
import com.notification.repo.irepo.ISettingRepository;
import com.notification.service.Iservice.ISettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
@Slf4j
public class SettingService implements ISettingService {

    final ISettingRepository settingRepo;

    public SettingService(ISettingRepository settingRepo) {
        this.settingRepo = settingRepo;
    }

    @Override
    public Output<DataSetting> getActiveStatus() {
        Output<DataSetting> output = new Output<>();
        try {
            List<Setting> data = settingRepo.getAllByActiveSettingOrderByCreationDateDesc(true);
            if (data.size() != 0) {
                output.setData(new DataSetting(data.get(0)));
            } else {
                output.setData(new DataSetting());// set all setting false
            }
        } catch (Exception e) {
            log.error(this.getClass().toString() + " : getActiveStatus" + ": \n" + e.getMessage());
            output.setData(new DataSetting());// set all setting false
            output.setSuccess(false);
            output.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        output.setMessage(MessageUtils.getMessage(output, new Locale("fa")));
        return output;
    }

    @Override
    public Output update(DataSetting obj) {
        Output output = new Output();
        try {
            Setting newSetting = obj.convertDtoToEntity();
            newSetting.setActiveSetting(true);

            List<Setting> oldSettings = getSettingForDeActive();
            oldSettings.add(newSetting);
            settingRepo.saveAll(oldSettings);
        } catch (Exception e) {
            log.error(this.getClass().toString() + " : update" + ": \n" + e.getMessage());
            output.getErrors().add(CodeException.SYSTEM_EXCEPTION);
            output.setSuccess(false);
        }
        output.setMessage(MessageUtils.getMessage(output, new Locale("fa")));
        return output;
    }

    private List<Setting> getSettingForDeActive() {
        List<Setting> oldSettings = settingRepo.getAllByActiveSettingOrderByCreationDateDesc(true);
        oldSettings.forEach(result -> {
            result.setLastUpdate(DateUtils.getCurrentDate());
            InfraUtility.giveCurrentUser().ifPresent(item -> result.setUpdaterUserId(item.getUserId()));
            result.setActiveSetting(false);
        });
        return oldSettings;
    }
}
