package com.env.utility;

import com.basedata.generalcode.CodeException;
import com.env.basedata.OccasionLengthTypeEnum;
import com.env.service.dto.BaseOccasionDto;
import com.env.service.dto.OccasionTypeDto;
import com.form.OutputAPIForm;
import com.utility.ImageUtility;
import com.utility.StringUtility;

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
        if(Objects.nonNull(dto.getSourceDto()) && Objects.isNull(dto.getSourceDto().getPlaceId())){
            retVal = retVal.isSuccess()? checkNull(dto.getSourceDto().getLongitude()):retVal;
            retVal = retVal.isSuccess()? checkNull(dto.getSourceDto().getLatitude()):retVal;
            retVal = retVal.isSuccess()? checkNull(dto.getSourceDto().getName()):retVal;
            retVal = retVal.isSuccess()? checkNull(dto.getSourceDto().getNameFa()):retVal;
        }
        return retVal;
    }

    public static void setLocaleInContext(){
//        SecurityContextHolder.getContext().getAuthentication().getPrincipal().
    }

    public static OutputAPIForm checkMandatoryOccasionTypeDto(OccasionTypeDto dto){
        OutputAPIForm retVal = StringUtility.checkString(dto.getOccasionTypeName(),false);
        retVal = retVal.isSuccess()? checkNull(dto.getPic()):retVal;
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
        if(retVal.isSuccess() && (Objects.nonNull(pic) || pic.length > 0)){
            retVal =ImageUtility.validateImage(pic,10000,10000);
        }
        return retVal;
    }

    public static OutputAPIForm checkOccasionDateTime(OccasionLengthTypeEnum occasionLengthType, Timestamp startDate, Timestamp endDate){
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
