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

/**
 * @author m.keyvanlou
 * @created 25/10/2022 - 12:08 PM
 **/

@Getter
@AllArgsConstructor
@Slf4j
public enum MsgStatusEnum {

    SUBMIT(0) ,
    DOING(1) ,
    SUCCESS(2) ,
    FAILED(3) ,
    DONE(4) ,
    CANCEL_BY_ADMIN(5) ,
    CANCEL(6),
    PAUSE(7),
    RESUME(8);

    public static String MSG_STATUS = "MsgStatus";
    public static String MSG_STATUS_ENUM = "msg_status_enum.";

    @JsonValue
    private int messageStatus;

    public static BaseData getMsgStatusEnum(){
        return new BaseData(MSG_STATUS,getMsgStatusEnumCode());
    }

    public static ArrayList<KeyValue> getMsgStatusEnumCode(){
        ArrayList<KeyValue> keyValue = new ArrayList<>();
        Arrays.asList(MsgStatusEnum.values()).forEach(item -> keyValue.add(item.getMsgStatusKeyValue()));
        return keyValue;
    }

    public static MsgStatusEnum loadByCode(Integer code){
        for(MsgStatusEnum statusEnum:MsgStatusEnum.values()){
            if(statusEnum.getMessageStatus() == code){
                return statusEnum;
            }
        }
        return null;
    }

    public KeyValue getMsgStatusKeyValue(){
        KeyValue keyValue = null;
        try{
            keyValue = new KeyValue(String.valueOf(this.getMessageStatus()), MessageUtils.getLocalizedMessage(MSG_STATUS_ENUM + this.toString().toLowerCase()  , new Locale("fa")));
        }catch (Exception e){
            log.error("Error in translate Enum msgStatusEnum...",e);
        }
        return keyValue;
    }


    @Override
    public String toString() {

        String retVal = null;
        switch (this.messageStatus) {

            case 0:
                retVal = "SUBMIT";
                break;
            case 1:
                retVal = "DOING";
                break;
            case 2:
                retVal = "SUCCESS";
                break;
            case 3:
                retVal = "FAILED";
                break;
            case 4:
                retVal = "DONE";
                break;
            case 5:
                retVal = "CANCEL_BY_ADMIN";
                break;
            case 6:
                retVal = "CANCEL";
                break;
            case 7:
                retVal = "PAUSE";
                break;
            case 8:
                retVal = "RESUME";
                break;
        }
        return retVal;
    }

}
