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
 * @created 25/10/2022 - 12:16 PM
 **/

@Getter
@AllArgsConstructor
@Slf4j
public enum AppTypeEnum {

    PWA(0) ,
    ANDROID(1) ,
    IOS(2);

    public static String APP_TYPE = "AppType";
    public static String APP_TYPE_ENUM = "app_type_enum.";

    @JsonValue
    private int appType;

    public static BaseData getAppTypeTypeEnum(){
        return new BaseData(APP_TYPE,getAppTypeEnumCode());
    }

    public static ArrayList<KeyValue> getAppTypeEnumCode(){
        ArrayList<KeyValue> keyValue = new ArrayList<>();
        Arrays.asList(AppTypeEnum.values()).forEach(item -> keyValue.add(item.getAppTypeKeyValue()));
        return keyValue;
    }

    public KeyValue getAppTypeKeyValue(){
        KeyValue keyValue = null;
        try{
            keyValue = new KeyValue(String.valueOf(this.getAppType()), MessageUtils.getLocalizedMessage(APP_TYPE_ENUM + this.toString().toLowerCase() , new Locale("fa")));
        }catch (Exception e){
            log.error("Error in translate Enum appTypeEnum...",e);
        }
        return keyValue;
    }

    public static AppTypeEnum getModel(Integer i){
        for(AppTypeEnum appTypeEnum:AppTypeEnum.values()){
            if(appTypeEnum.getAppType() == i){
                return appTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {

        String retVal;
        switch (this.appType) {
            case 1:
                retVal = "ANDROID";
                break;
            case 2:
                retVal = "IOS";
                break;
            case 0:
            default:
                retVal = "PWA";
        }
        return retVal;
    }
}
