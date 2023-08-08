package com.basedata;

public enum OccasionLengthTypeEnum {
    Daily(0),Hourly(1);

    int occasionLengthTypeCode;
    OccasionLengthTypeEnum(int occasionLengthTypeCode){
        this.occasionLengthTypeCode  = occasionLengthTypeCode;
    }


}
