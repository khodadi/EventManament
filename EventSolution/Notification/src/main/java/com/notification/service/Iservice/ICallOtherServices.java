package com.notification.service.Iservice;

import com.infra.dto.BaseData;
import com.infra.dto.Output;
import com.notification.dto.AppNotificationMaster;

import java.util.List;

/**
 * @Creator 1/14/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface ICallOtherServices {
    Output<BaseData> getUsers();
    Output<AppNotificationMaster>  saveAndupdateDataMasterInServiceBi(AppNotificationMaster appNotificationMaster, boolean flag);
    List<AppNotificationMaster> loadDataMasterFromWebServiceBI(List<Long> ids);
    Output<List<String>> getUserPermission(String applicationName);
}
