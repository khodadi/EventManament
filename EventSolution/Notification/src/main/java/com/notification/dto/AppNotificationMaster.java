package com.notification.dto;

import com.notification.basedata.AppTypeEnum;
import com.notification.basedata.ServiceTypeEnum;
import com.notification.entity.RequestMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @Creator :  3/4/2023, Saturday
 * @Project : IntelliJ IDEA
 * @Author : Z.Sahraee
 **/

@Setter
@Getter
@AllArgsConstructor
public class AppNotificationMaster extends DataRequestMaster {
    private Long trackingCode;
    private Integer status;
    private Integer serviceStatus;
    private Long successCount;
    private Long failedCount;
    private Integer page;
    private Integer size;

    public AppNotificationMaster(RequestMaster master) {
        super(master);
        this.status = master.getStatus().getMessageStatus();
        this.serviceStatus = master.getServiceStatus().getActivationStatusCode();
        this.page = 0;
        this.size=10;
    }

    public AppNotificationMaster(ServiceTypeEnum serviceType, String title, String body,
                                 AppTypeEnum appType, Long businessCategoryId, Long pspId, Long provinceId, Long terminalType,
                                 Long merchantType, String terminalNumber, Long residencyType, String nationalCode,
                                 Timestamp startDateSend, Timestamp startTimeSend, Timestamp endDateSend, Timestamp endTimeSend) {

        super(serviceType, title, body, appType, businessCategoryId, pspId, provinceId, terminalType,
              merchantType, terminalNumber, residencyType, nationalCode,
              startDateSend, startTimeSend, endDateSend,  endTimeSend);
        this.page = 0;
        this.size=10;
    }

    public AppNotificationMaster() {
        this.page = 0;
        this.size=5;
    }

    public AppNotificationMaster(Long trackingCode) {
        this.trackingCode = trackingCode;
        this.page = 0;
        this.size=5;
    }

    public AppNotificationMaster(Long trackingCode, Integer serviceStatus, Integer status) {
        this.trackingCode = trackingCode;
        this.serviceStatus = serviceStatus;
        this.status = status;
    }

    public AppNotificationMaster(Long trackingCode, Integer status) {
        this.trackingCode = trackingCode;
        this.status = status;
    }
}
