package com.notification.dto;

import com.notification.basedata.ServiceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestMasterSearch {
    private Long requestMasterId;
    private Timestamp startDateSend;
    private Timestamp endDateSend;
    private Long userId;
    private ServiceTypeEnum serviceType;
    private Integer page=0;
    private Integer size=10;
    @Pattern(regexp = "^[a-z A-Z]*$",message = "sort_validcharacter:40200030")
    private String sort = "requestMasterId desc ";

    public RequestMasterSearch(Timestamp startDateSend, Timestamp endDateSend, long userId, ServiceTypeEnum serviceType, Integer page, Integer size) {
        this.startDateSend = startDateSend;
        this.endDateSend = endDateSend;
        this.userId = userId;
        this.serviceType = serviceType;
        this.page = page;
        this.size = size;
    }
}
