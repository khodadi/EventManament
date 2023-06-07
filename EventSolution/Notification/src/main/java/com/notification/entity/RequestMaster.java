package com.notification.entity;

import com.infra.basedata.ActivationStatusEnum;
import com.infra.entity.ABaseEntity;
import com.notification.basedata.AppTypeEnum;
import com.notification.basedata.MsgStatusEnum;
import com.notification.basedata.ServiceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author m.keyvanlou
 * @created 25/10/2022 - 8:03 AM
 **/

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REQUEST_MASTER" , schema = "NOTIF_USER")
public class RequestMaster extends ABaseEntity{

    @Id
    @Column(name = "REQUEST_MASTER_ID", nullable = false, insertable = true, updatable = false, precision = 0)
    @GeneratedValue(generator = "REQUEST_MASTER_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "REQUEST_MASTER_SEQ", allocationSize = 1, sequenceName = "REQUEST_MASTER_SEQ",schema = "NOTIF_USER")
    private Long requestMasterId;

    @Basic
    @Column(name = "SERVICETYPE", nullable = false)
    private ServiceTypeEnum serviceType;

    @Basic
    @Column(name = "TITLE", length = 300)
    private String title;

    @Basic
    @Column(name = "SERVICE_STATUS")
    private ActivationStatusEnum serviceStatus;

    @Basic
    @Column(name = "BODY", nullable = false, length = 4000)
    private String body;

    @Basic
    @Column(name = "STATUS", nullable = false,  length = 1)
    private MsgStatusEnum status;

    @Basic
    @Column(name = "SUCCESS_COUNT")
    private Long successCount;

    @Basic
    @Column(name = "FAILED_COUNT")
    private Long failedCount;

    @Basic
    @Column(name = "APP_TYPE", nullable = false, length = 1)
    private AppTypeEnum appType;

    @Basic
    @Column(name = "BUSINESS_CATEGORY_ID")
    private Long businessCategoryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS_CATEGORY_ID", referencedColumnName = "BUSINESS_CATEGORY_ID", insertable = false, updatable = false,foreignKey=@ForeignKey(name = "FK_From_BusinessCategory_To_RequestMaster"))
    private BusinessCategory businessCategoryByBusinessCategoryId;

    @Basic
    @Column(name = "PSP_ID")
    private Long pspId;
    @OneToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "PSP_ID",referencedColumnName = "PSP_ID", insertable = false, updatable = false,foreignKey=@ForeignKey(name = "FK_From_PSP_To_RequestMaster"))
    private PSP pspByPspId;

    @Basic
    @Column(name = "PROVINCE_ID")
    private Long provinceId;
    @OneToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVINCE_ID",referencedColumnName = "PROVINCE_ID", insertable = false, updatable = false,foreignKey=@ForeignKey(name = "FK_From_Province_To_RequestMaster"))
    private Province provinceByProvinceId;

    @Basic
    @Column(name = "TERMINAL_TYPE")
    private Long terminalType;

    @Basic
    @Column(name = "TERMINAL_NUMBER",length = 8)
    private String terminalNumber;

    @Basic
    @Column(name = "MERCHANT_TYPE")
    private Long merchantType;

    @Basic
    @Column(name = "RESIDENCY_TYPE")
    private Long residencyType;

    @Basic
    @Column(name = "NATIONAL_CODE" ,length = 10)
    private String nationalCode;

    @Basic
    @Column(name = "TRACKING_CODE")
    private Long trackingCode;

    @Basic
    @Column(name = "START_DATE_SEND")
    private Timestamp startDateSend;
    @Basic
    @Column(name = "END_DATE_SEND")
    private Timestamp endDateSend;
    @Basic
    @Column(name = "START_TIME_SEND")
    private Timestamp startTimeSend;
    @Basic
    @Column(name = "END_TIME_SEND")
    private Timestamp endTimeSend;

}
