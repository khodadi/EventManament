package com.env.service.dto;

import com.env.basedata.OccasionLengthTypeEnum;
import com.env.utility.Utility;
import com.form.OutputAPIForm;
import com.utility.StringUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseOccasionDto {
    private String occasionName;
    private Long occasionTypeId;
    private OccasionLengthTypeEnum occasionLengthType;
    private Timestamp startDate;
    private Timestamp endDate;
    private Boolean sharable;
    private byte[] pic;
    private Long sourceId;
    private PlaceDto sourceDto;


    public  OutputAPIForm checkMandatoryForInsert(){
        OutputAPIForm retVal = StringUtility.checkString(getOccasionName());
        retVal = retVal.isSuccess()? Utility.checkNull(getOccasionLengthType()):retVal;
        retVal = retVal.isSuccess()? Utility.checkNull(getStartDate()):retVal;
        retVal = retVal.isSuccess()? Utility.checkNull(getEndDate()):retVal;
        retVal = retVal.isSuccess()? Utility.checkNull(getSharable()):retVal;
        retVal = retVal.isSuccess()? Utility.checkNull(getPic()):retVal;
        retVal = retVal.isSuccess()? Utility.checkNull(getSourceId()):retVal;
        return retVal;
    }
    public OutputAPIForm checkFormatData(){
        OutputAPIForm retVal = new OutputAPIForm();
           retVal = retVal.isSuccess() ? StringUtility.checkString(getOccasionName(),1,50,false):retVal;
        if(retVal.isSuccess() && Objects.nonNull(getOccasionLengthType()) && Objects.nonNull(getOccasionLengthType()) && Objects.nonNull(getOccasionLengthType())){
            retVal =  Utility.checkOccasionDateTime(getOccasionLengthType(), getStartDate(), getEndDate());
        }
        retVal = (retVal.isSuccess() &&  Objects.nonNull(getPic())) ? Utility.checkPic(getPic(),true):retVal;
        return retVal;
    }
}
