package com.notification.job;

import com.infra.dto.Output;
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

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveMasterToFaragir {
    private final ISettingService settingService;
    private final ICallOtherServices callOtherServices;
    private final IRequestMasterService requestMasterService;

    @Scheduled(cron = "${job.request.reader}")
    public void saveMasterToFaragir() {
        Output<DataSetting> setting = new Output<>();
        try {
            setting = settingService.getActiveStatus();
            if (InfraUtility.checkValidInetAddress(setting.getData().getIpAddressValidSaveMaster())) {
                log.info("this is doing readRequest for this job save.");
                if (setting.getData().isActiveSaveMaster()) {
                    if (setting.getData().isActiveSms() || setting.getData().isActiveEmail() ||
                            setting.getData().isActiveNotification() || setting.getData().isActiveAlarm()) {

                        Output<AppNotificationMaster> master = new Output<>();
                        do {
                            try {
                                master = requestMasterService.getAndUpdateMaster(setting.getData());
                                if (master.isSuccess() && master.getData() != null) {
                                    boolean check = sendMasterToFaragir(master.getData());
                                    if (!check) {
                                        AtomicReference<Long> currentUser = new AtomicReference<>(1L);
                                        InfraUtility.giveCurrentUser().ifPresent(item -> currentUser.set(item.getUserId()));
                                        requestMasterService.updateStatus(MsgStatusEnum.SUBMIT, master.getData().getRequestMasterId(),currentUser.get() );
                                    }
                                }
                            } catch (Exception e) {
                                log.error("RequestSenderJob - send Request error :\n" + e);
                            }
                        } while (master.isNextPage());

                    }
                }else {
                    log.error("SaveMasterToFaragir job isn't active!");
                }
            }else {
                log.error("IPAddress is invalid!");
            }
        } catch (Exception e) {
            log.error("SaveMasterToFaragir - read RequestMaster data error :\n" + e);
        }
    }

    public boolean sendMasterToFaragir(AppNotificationMaster input) {
        Output<AppNotificationMaster> result;
        try {
            boolean flag = true;
            AppNotificationMaster dto = new AppNotificationMaster(input.getServiceType(), input.getTitle(), input.getBody(),
                    input.getAppType(), input.getBusinessCategoryId(), input.getPspId(), input.getProvinceId(), input.getTerminalType(),
                    input.getMerchantType(), input.getTerminalNumber(), input.getResidencyType(), input.getNationalCode(),
                    input.getStartDateSend(), input.getStartTimeSend(), input.getEndDateSend(), input.getEndTimeSend());
            result = callOtherServices.saveAndupdateDataMasterInServiceBi(dto, flag);
            requestMasterService.updateTrackingCode( input.getRequestMasterId(),result.getData().getTrackingCode());
            return result.isSuccess();
        } catch (Exception e) {
            log.error("SenderDataService - Sender Data error :\n" + e);
            return false;
        }
    }
}
