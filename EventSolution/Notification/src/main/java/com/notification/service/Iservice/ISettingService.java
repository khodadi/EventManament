package com.notification.service.Iservice;

import com.infra.dto.Output;
import com.notification.dto.DataSetting;

public interface ISettingService {

    Output<DataSetting> getActiveStatus();

    Output update(DataSetting dataSetting);

}
