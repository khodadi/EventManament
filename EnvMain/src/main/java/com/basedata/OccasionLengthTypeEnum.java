package com.basedata;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OccasionLengthTypeEnum {
    Daily(0),Hourly(1);
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
}
