package com.notification.basedata;

import com.fasterxml.jackson.annotation.JsonValue;
import com.infra.dto.BaseData;
import com.infra.dto.KeyValue;
import com.infra.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@Getter
@AllArgsConstructor
@Slf4j
public enum ScheduleChoiceEnum {

    YESTERDAY(0) ,
    BEFORE_WEEK(1) ,
    BEFORE_MONTH(2) ,
    BOUND_DATE(3) ;

    public static String SCHEDULE_CHOICE = "ScheduleChoice";
    public static String SCHEDULE_CHOICE_ENUM = "schedule_choice_enum.";

    @JsonValue
    private int scheduleChoice;

    public static BaseData getScheduleChoiceEnum(){
        return new BaseData(SCHEDULE_CHOICE,getScheduleChoiceEnumCode());
    }

    public static ArrayList<KeyValue> getScheduleChoiceEnumCode(){
        ArrayList<KeyValue> keyValue = new ArrayList<>();
        Arrays.asList(ScheduleChoiceEnum.values()).forEach(item -> keyValue.add(item.getScheduleChoiceKeyValue()));
        return keyValue;
    }

    public KeyValue getScheduleChoiceKeyValue(){
        KeyValue keyValue = null;
        try{
            keyValue = new KeyValue(String.valueOf(this.getScheduleChoice()), MessageUtils.getLocalizedMessage(SCHEDULE_CHOICE_ENUM + this.toString().toLowerCase()  , new Locale("fa")));
        }catch (Exception e){
            log.error("Error in translate Enum ScheduleChoiceEnum...",e);
        }
        return keyValue;
    }


    @Override
    public String toString() {

        String retVal = null;
        switch (this.scheduleChoice) {

            case 0:
                retVal = "YESTERDAY";
                break;
            case 2:
                retVal = "BEFORE_MONTH";
                break;
            case 3:
                retVal = "BOUND_DATE";
                break;
            case 1:
            default:
                retVal = "BEFORE_WEEK";

        }
        return retVal;
    }

}
