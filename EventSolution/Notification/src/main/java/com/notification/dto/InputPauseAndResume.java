package com.notification.dto;

import com.notification.basedata.MsgStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Creator :  5/2/2023, Tuesday
 * @Project : IntelliJ IDEA
 * @Author : Z.Sahraee
 **/
@Setter
@Getter
@NoArgsConstructor
public class InputPauseAndResume {
    private Long requestMasterId;
    private MsgStatusEnum status;

    public InputPauseAndResume(Long requestMasterId, MsgStatusEnum status) {
        this.requestMasterId = requestMasterId;
        this.status = status;
    }
}
