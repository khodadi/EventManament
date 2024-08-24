package com.env.basedata;

import com.fasterxml.jackson.annotation.JsonValue;
import com.service.dto.BaseData;
import com.service.dto.KeyValue;
import com.service.services.IMessageBundleSrv;
import com.utility.GeneralUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

public enum OccasionLengthTypeEnum {

    Daily(0),Hourly(1);

    public static final String OCCASION_LENGTH_KEY = "OccasionLengthType";

    @Autowired
    private IMessageBundleSrv message;
    int occasionLengthTypeCode;
    OccasionLengthTypeEnum(int occasionLengthTypeCode){
        this.occasionLengthTypeCode  = occasionLengthTypeCode;
    }
    public void setOccasionLengthTypeCode(int occasionLengthTypeCode) {
        this.occasionLengthTypeCode = occasionLengthTypeCode;
    }
    @JsonValue
    public int getOccasionLengthTypeCode() {
        return occasionLengthTypeCode;
    }

    public static BaseData getLovOccasionType(){
        BaseData retVal = new BaseData(OCCASION_LENGTH_KEY,new ArrayList<>());
        IMessageBundleSrv messageBundleSrv =GeneralUtility.getMessageSrv();
        for(OccasionLengthTypeEnum occasionLength:OccasionLengthTypeEnum.values()){
            try{
                retVal.getLov().add(new KeyValue( occasionLength.getOccasionLengthTypeCode(),
                        messageBundleSrv!=null?messageBundleSrv.getMessage((OCCASION_LENGTH_KEY+"."+occasionLength).toLowerCase()):occasionLength.toString()
                        )
                );
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return retVal;
    }

    @Override
    public String toString() {
        String retVal = "Undefined";
        switch (getOccasionLengthTypeCode()){
            case 0:
                retVal = "Daily";
                break;
            case 1:
                retVal = "Hourly";
                break;
        }
        return retVal;
    }
}
