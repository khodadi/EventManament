package com.notification.utils;

import com.infra.utils.DateUtils;
import com.notification.basedata.ScheduleChoiceEnum;
import com.notification.dto.BoundDate;

/**
 * @Creator 1/14/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public class Utility {
    public static void computingDate(BoundDate boundDate){
        if(ScheduleChoiceEnum.YESTERDAY.equals(boundDate.getScheduleChoiceEnum())){
            boundDate.setStartDateSend(DateUtils.getBeginOfDay(DateUtils.addDays(DateUtils.getCurrentDate(),-1)));
            boundDate.setEndDateSend(DateUtils.getEndOfDay(DateUtils.getCurrentDate()));
        }else if (ScheduleChoiceEnum.BEFORE_WEEK.equals(boundDate.getScheduleChoiceEnum())){
            boundDate.setStartDateSend(DateUtils.getBeginOfDay(DateUtils.addDays(DateUtils.getCurrentDate(),-7)));
            boundDate.setEndDateSend(DateUtils.getEndOfDay((DateUtils.getCurrentDate())));
        }else if (ScheduleChoiceEnum.BEFORE_MONTH.equals(boundDate.getScheduleChoiceEnum())){
            boundDate.setStartDateSend(DateUtils.getBeginOfDay(DateUtils.addDays(DateUtils.getCurrentDate(),-30)));
            boundDate.setEndDateSend(DateUtils.getEndOfDay((DateUtils.getCurrentDate())));
        }
    }
}
