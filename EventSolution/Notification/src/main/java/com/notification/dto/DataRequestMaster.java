package com.notification.dto;

import com.infra.basedata.ActivationStatusEnum;
import com.infra.utils.DateUtils;
import com.infra.utils.InfraUtility;
import com.infra.utils.validate.NationalCode;
import com.infra.utils.validate.TerminalNumber;
import com.notification.basedata.AppTypeEnum;
import com.notification.basedata.MsgStatusEnum;
import com.notification.basedata.ServiceTypeEnum;
import com.notification.entity.RequestMaster;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
public class DataRequestMaster {
    private Long requestMasterId;
    @NotNull(message = "requestmasterdto_servicetype_notnull:40200001")
    private ServiceTypeEnum serviceType;
    @Length(message = "notification_title_length:40200002" , max = 300)
    private String title;
    @Length(message = "requestmasterdto_body:40200003"/*,min = 10*/ , max = 4000)
    @NotNull(message = "requestmasterdto_body_notnull:40200004")
    private String body;

    @NotNull(message = "requestmasterdto_apptype_notnull:40200005")
    private AppTypeEnum appType;
    private Long businessCategoryId;
    private Long pspId;
    private Long provinceId;
    private Long terminalType;
    private Long merchantType;
    @TerminalNumber
    private String terminalNumber;
    private Long residencyType;
    @NationalCode
    private String nationalCode;

    @NotNull(message = "requestmasterdto_startdatesend_notnull:40200006")
    private Timestamp startDateSend;
    @NotNull(message = "requestmasterdto_starttimesend_notnull:40200007")
    private Timestamp startTimeSend;
    @NotNull(message = "requestmasterdto_enddatesend_notnull:40200008")
    private Timestamp endDateSend;
    @NotNull(message = "requestmasterdto_endtimesend_notnull:4020009")
    private Timestamp endTimeSend;

    public DataRequestMaster(RequestMaster master) {
        this.requestMasterId = master.getRequestMasterId();
        this.serviceType = master.getServiceType();
        this.title = master.getTitle();
        this.body = master.getBody();
        this.appType =master.getAppType();
        this.businessCategoryId = master.getBusinessCategoryId();
        this.pspId = master.getPspId();
        this.provinceId = master.getProvinceId();
        this.terminalType = master.getTerminalType();
        this.terminalNumber = master.getTerminalNumber();
        this.merchantType = master.getMerchantType();
        this.residencyType = master.getResidencyType();
        this.nationalCode = master.getNationalCode();

        this.startDateSend = master.getStartDateSend();
        this.startTimeSend = master.getStartTimeSend();
        this.endDateSend = master.getEndDateSend();
        this.endTimeSend = master.getEndTimeSend();
    }

    public DataRequestMaster(ServiceTypeEnum serviceType, String title, String body,
                             AppTypeEnum appType, Long businessCategoryId, Long pspId, Long provinceId, Long terminalType,
                             Long merchantType, String terminalNumber, Long residencyType, String nationalCode,
                             Timestamp startDateSend, Timestamp startTimeSend, Timestamp endDateSend, Timestamp endTimeSend) {
        this.serviceType = serviceType;
        this.title = title;
        this.body = body;
        this.appType = appType;
        this.businessCategoryId = businessCategoryId;
        this.pspId = pspId;
        this.provinceId = provinceId;
        this.terminalType = terminalType;
        this.merchantType = merchantType;
        this.terminalNumber = terminalNumber;
        this.residencyType = residencyType;
        this.nationalCode = nationalCode;
        this.setStartDateSend(startDateSend);
        this.setStartTimeSend(startTimeSend);
        this.setEndDateSend(endDateSend);
        this.setEndTimeSend(endTimeSend);
    }


    public RequestMaster convertDtoToEntity() {
        RequestMaster obj = new RequestMaster();
        obj.setServiceType(this.serviceType);
        obj.setTitle(this.title);
        obj.setBody(this.body);

        obj.setAppType(this.appType);
        obj.setBusinessCategoryId(this.businessCategoryId);
        obj.setPspId(this.pspId);
        obj.setProvinceId(this.provinceId);
        obj.setTerminalType(this.terminalType);
        obj.setTerminalNumber(this.terminalNumber);
        obj.setMerchantType(this.merchantType);
        obj.setResidencyType(this.residencyType);
        obj.setNationalCode(this.nationalCode);

        obj.setStartDateSend(this.startDateSend);
        obj.setEndDateSend(this.endDateSend);
        obj.setStartTimeSend(this.startTimeSend);
        obj.setEndTimeSend(this.endTimeSend);

        obj.setStatus(MsgStatusEnum.SUBMIT);
        obj.setServiceStatus(ActivationStatusEnum.Active);
        obj.setSuccessCount(0L);
        obj.setFailedCount(0L);
        obj.setTrackingCode(0L);

        if (obj.getRequestMasterId() == null) {
            InfraUtility.giveCurrentUser().ifPresent(item -> obj.setCreatorUserId(item.getUserId()));
            obj.setLastUpdate(null);
        }
        return obj;
    }

    public void convertDtoToEntity(RequestMaster master) {
        master.setRequestMasterId(this.requestMasterId);
        master.setServiceType(this.serviceType);
        master.setTitle(this.title);
        master.setBody(this.body);
        master.setAppType(this.appType);
        master.setBusinessCategoryId(this.businessCategoryId);
        master.setPspId(this.pspId);
        master.setProvinceId(this.provinceId);
        master.setTerminalType(this.terminalType);
        master.setTerminalNumber(this.terminalNumber);
        master.setMerchantType(this.merchantType);
        master.setResidencyType(this.residencyType);
        master.setNationalCode(this.nationalCode);
        master.setStartDateSend(this.startDateSend);
        master.setEndDateSend(this.endDateSend);
        master.setStartTimeSend(this.startTimeSend);
        master.setEndTimeSend(this.endTimeSend);
        if (master.getRequestMasterId() != null) { // in update
            InfraUtility.giveCurrentUser().ifPresent(item->master.setUpdaterUserId(item.getUserId()));
            master.setLastUpdate(DateUtils.getCurrentDate());
        } else {                                // in save
            InfraUtility.giveCurrentUser().ifPresent(item->master.setCreatorUserId(item.getUserId()));
            master.setLastUpdate(null);
        }
    }

}
