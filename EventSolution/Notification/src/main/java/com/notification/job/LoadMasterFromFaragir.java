package com.notification.job;

import com.infra.dto.Output;
import com.infra.utils.DateUtils;
import com.infra.utils.InfraUtility;
import com.notification.basedata.MsgStatusEnum;
import com.notification.dto.AppNotificationMaster;
import com.notification.dto.DataSetting;
import com.notification.service.Iservice.ICallOtherServices;
import com.notification.service.Iservice.IRequestMasterService;
import com.notification.service.Iservice.ISettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Creator :  2/12/2023, Sunday
 * @Project : IntelliJ IDEA
 * @Author : Z.Sahraee
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadMasterFromFaragir {

    private final ICallOtherServices callOtherServices;
    private final IRequestMasterService requestMasterService;
    private final ISettingService settingService;

    @Scheduled(cron = "${job.request.reader}")
    public void loadMasterFromFaragir() {
        Output<DataSetting> setting = new Output<>();
        try {
            setting = settingService.getActiveStatus();
            if (InfraUtility.checkValidInetAddress(setting.getData().getIpAddressValidLoadMaster())) {
                log.info("job readRequest Fire at " + DateUtils.getCurrentDate());
                if (setting.getData().isActiveLoadMaster()) {
                    List<AppNotificationMaster> faragirData;
                    AppNotificationMaster appNotificationMaster = new AppNotificationMaster();
                    List<AppNotificationMaster> appNotificationMasters = requestMasterService.getAllTrackingCodeByStatus(appNotificationMaster);
                    List<Long> ids = appNotificationMasters.stream().map(AppNotificationMaster::getTrackingCode).collect(Collectors.toList());
                    log.info("job look up masterIds size: " + ids.size());
                    if (!ids.isEmpty()) {
                        try {
                            faragirData = callOtherServices.loadDataMasterFromWebServiceBI(ids);
                            log.info("job look up faragirData size: " + faragirData.size());
                            faragirData.forEach(item -> {
                                if (item.getStatus().equals(MsgStatusEnum.SUBMIT.getMessageStatus())) {
                                    item.setStatus(MsgStatusEnum.DOING.getMessageStatus());
                                }
                                requestMasterService.updateSuccessAndFailed(item, MsgStatusEnum.loadByCode(item.getStatus()));
                            });
                        } catch (Exception e) {
                            log.error("LoadMasterFromFaragir - send Request error :\n" + e);
                        }
                    }
                }else {
                    log.error("LoadMasterFromFaragir job isn't active!");
                }
            }else {
                log.error("IPAddress is invalid!");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

}