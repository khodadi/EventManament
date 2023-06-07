package com.notification.basedata;

import com.fasterxml.jackson.annotation.JsonValue;
import com.infra.dto.BaseData;
import com.infra.dto.KeyValue;
import com.infra.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author m.keyvanlou
 * @created 25/10/2022 - 11:58 AM
 **/

@Getter
@AllArgsConstructor
@Slf4j
public enum ServiceTypeEnum {

    SMS(0) ,
    EMAIL(1) ,
    NOTIFICATION(2),
    ALARM(3);

    public static String SERVICE_NAME = "ServiceType";
    public static String SERVICE_TYPE_ENUM = "service_type_enum.";

    @JsonValue
    private int serviceType;

    public static BaseData getServiceTypeEnum(){
        return new BaseData(SERVICE_NAME,getServiceTypeEnumCode());
    }

    public static ArrayList<KeyValue> getServiceTypeEnumCode(){
        ArrayList<KeyValue> keyValue = new ArrayList<>();
        Arrays.asList(ServiceTypeEnum.values()).forEach(item -> keyValue.add(item.getServiceTypeKeyValue()));
        return keyValue;
    }

    public KeyValue getServiceTypeKeyValue(){
       KeyValue keyValue = null;
            try{
                keyValue = new KeyValue(String.valueOf(this.getServiceType()), MessageUtils.getLocalizedMessage(SERVICE_TYPE_ENUM + this.toString().toLowerCase() , new Locale("fa")));
            }catch (Exception e){
                log.error("Error in translate Enum serviceTypeEnum...",e);
            }
        return keyValue;
    }

    public static ServiceTypeEnum getModel(Integer i){
        for(ServiceTypeEnum serviceTypeEnum:ServiceTypeEnum.values()){
            if(serviceTypeEnum.getServiceType() == i){
                return serviceTypeEnum;
            }
        }
        return null;
    }

    public static HashMap<String,String> getDataHashMap(){
        HashMap<String,String> map = new HashMap<>();
        for(ServiceTypeEnum serviceTypeEnum:ServiceTypeEnum.values()){
            try{
                map.put(serviceTypeEnum.toString(), MessageUtils.getLocalizedMessage(SERVICE_TYPE_ENUM + serviceTypeEnum.toString().toLowerCase() , new Locale("fa")));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
            return map;
    }


    @Override
    public String toString() {
        String retVal;
        switch (this.serviceType) {
            case 0:
                retVal = "SMS";
                break;
            case 1:
                retVal = "EMAIL";
                break;
            case 3:
                retVal = "ALARM";
                break;
            case 2:
            default:
                retVal = "NOTIFICATION";
        }
        return retVal;
    }
}
