package com.env.dao.entity;

import com.env.basedata.StateRequest;
import com.env.service.dto.OccasionUsersDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OCCASION_USERS",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccasionUsers extends ABaseEntity{
    @Id
    @Column(name = "OCCASION_USERS_ID")
    @GeneratedValue(generator = "SEQ_OCCASION_USERS", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_OCCASION_USERS", allocationSize = 1, sequenceName = "SEQ_OCCASION_USERS",schema = "ENV_DATA")
    private Long occasionUserId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "OCCASION_ID")
    private Long occasionId;
    @Column(name = "STATE_REQUEST")
    private StateRequest stateRequest;
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    @Column(name = "SEND_SMS")
    private boolean sendSms;

    public OccasionUsers(OccasionUsersDto dto) {

        this.userId = dto.getUserId();
        this.occasionId = dto.getOccasionId();
        this.stateRequest = dto.getStateRequest();
        this.mobileNumber = dto.getMobileNumber();
        this.sendSms = dto.isSendSMS();
    }
}
