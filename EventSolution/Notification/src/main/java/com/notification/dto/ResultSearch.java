package com.notification.dto;


import com.notification.basedata.ServiceTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class ResultSearch {
    private ServiceTypeEnum serviceType;
    private Long failedCount=0L;
    private Long successCount=0L;

    public ResultSearch(ServiceTypeEnum serviceType, Long failedCount ,Long successCount) {
        this.serviceType = serviceType;
        this.failedCount = failedCount;
        this.successCount = successCount;
    }

}
