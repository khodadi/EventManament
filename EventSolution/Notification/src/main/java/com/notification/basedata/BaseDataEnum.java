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
 * @Creator :  3/28/2023, Tuesday
 * @Project : IntelliJ IDEA
 * @Author : Z.Sahraee
 **/

@Getter
@AllArgsConstructor
@Slf4j
public enum BaseDataEnum {
    BusinessCategory(0) ,
    PSP(1) ,
    Province(2) ,
    MerchantType(3) ,
    ResidencyType(4) ,
    TerminalType(5);

    public static String BASE_DATA = "BaseData";
    public static String BASE_DATA_ENUM = "base_data_enum.";

    @JsonValue
    private int baseData;

    public static BaseData getBaseDataEnum(){
        return new BaseData(BASE_DATA,getBaseDataEnumCode());
    }

    public static ArrayList<KeyValue> getBaseDataEnumCode(){
        ArrayList<KeyValue> keyValue = new ArrayList<>();
        Arrays.asList(BaseDataEnum.values()).forEach(item -> keyValue.add(item.getBaseDataKeyValue()));
        return keyValue;
    }

    public KeyValue getBaseDataKeyValue(){
        KeyValue keyValue = null;
        try{
            keyValue = new KeyValue(String.valueOf(this.getBaseData()), MessageUtils.getLocalizedMessage(BASE_DATA_ENUM + this.toString().toLowerCase() , new Locale("fa")));
        }catch (Exception e){
            log.error("Error in translate Enum serviceTypeEnum...",e);
        }
        return keyValue;
    }

    @Override
    public String toString() {
        String retVal = null;
        switch (this.baseData) {
            case 0:
                retVal = "BusinessCategory";
                break;
            case 1:
                retVal = "PSP";
                break;
            case 2:
                retVal = "Province";
                break;
            case 3:
                retVal = "MerchantType";
                break;
            case 4:
                retVal = "ResidencyType";
                break;
            case 5:
                retVal = "TerminalType";
                break;
        }
        return retVal;
    }
}
