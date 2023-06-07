package com.notification.dto;

import com.notification.basedata.MsgStatusEnum;
import com.notification.entity.RequestMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Creator 1/14/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TotalRequestMaster extends DataRequestMaster {

    private Long userId;
    private MsgStatusEnum status;
    private Long successCount;
    private Long failedCount;

    public TotalRequestMaster(RequestMaster master) {
        super(master);
        this.userId = master.getCreatorUserId();
        this.status = master.getStatus();
        this.successCount = master.getSuccessCount();
        this.failedCount = master.getFailedCount();
    }

}
