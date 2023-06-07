package com.notification.dto;

import com.notification.basedata.ScheduleChoiceEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoundDate {

    ScheduleChoiceEnum scheduleChoiceEnum;
    private Timestamp startDateSend;
    private Timestamp endDateSend;

    public BoundDate(ScheduleChoiceEnum scheduleChoice) {
        this.scheduleChoiceEnum = scheduleChoice;
    }
}
