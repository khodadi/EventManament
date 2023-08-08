package com.utility;

import com.api.form.OutputAPIForm;
import com.basedata.CodeException;
import com.basedata.OccasionLengthTypeEnum;
import com.service.dto.BaseOccasionDto;

import java.sql.Timestamp;
import java.util.Objects;

public class Utility {

    public static OutputAPIForm checkMandatoryBaseOccasion(BaseOccasionDto dto){
        OutputAPIForm retVal = StringUtility.checkString(dto.getOccasionName(),false);
        retVal = retVal.isSuccess()? checkNull(dto.getOccasionLengthType()):retVal;
        retVal = retVal.isSuccess()? checkNull(dto.getStartDate()):retVal;
        retVal = retVal.isSuccess()? checkNull(dto.getEndDate()):retVal;
        retVal = retVal.isSuccess()? checkNull(dto.getSharable()):retVal;
        retVal = retVal.isSuccess()? checkNull(dto.getPic()):retVal;
        retVal = retVal.isSuccess()? checkNull(dto.getLatitude()):retVal;
        retVal = retVal.isSuccess()? checkNull(dto.getLongitude()):retVal;
        return retVal;
    }
    public static OutputAPIForm checkPic(byte[] pic, boolean isNull){
        OutputAPIForm retVal = new OutputAPIForm<>();
        if(!isNull){
            if(Objects.isNull(pic) || pic.length == 0){
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.MANDATORY_FIELD);
            }
        }
        return retVal;
    }

    public static OutputAPIForm checkOccasionDateTime(OccasionLengthTypeEnum occasionLengthType, Timestamp startDate,Timestamp endDate){
        OutputAPIForm retVal = new OutputAPIForm<>();
        if(Objects.isNull(occasionLengthType) || Objects.isNull(startDate) || Objects.isNull(endDate)){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.MANDATORY_FIELD);
        }else if(occasionLengthType.equals(OccasionLengthTypeEnum.Daily)){
            retVal = checkOccasionDate(startDate,endDate);
        }else if(occasionLengthType.equals(OccasionLengthTypeEnum.Hourly)){
            retVal = checkOccasionTime(startDate,endDate);
        }
        return retVal;
    }
    public static OutputAPIForm checkOccasionDate( Timestamp startDate,Timestamp endDate){
        OutputAPIForm retVal = new OutputAPIForm<>();
        if(endDate.before(startDate)){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.INVALIDATE_DATETIME);
        }
        return retVal;
    }
    public static OutputAPIForm checkOccasionTime( Timestamp startDate,Timestamp endDate){
        OutputAPIForm retVal = new OutputAPIForm<>();
        if(endDate.before(startDate)){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.INVALIDATE_DATETIME);
        }
        return retVal;
    }

    public static OutputAPIForm checkNull(Object obj){
        OutputAPIForm retVal = new OutputAPIForm<>();
        if(Objects.isNull(obj) ){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.MANDATORY_FIELD);
        }

        return retVal;
    }

}
