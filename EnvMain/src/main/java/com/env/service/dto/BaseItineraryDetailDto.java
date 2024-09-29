package com.env.service.dto;

import com.env.utility.Utility;
import com.form.OutputAPIForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseItineraryDetailDto {
    private Long itineraryDetailId;
    private Long sourceId;
    private Long occasionId;
    private Long itineraryId;
    private ArrayList<Long> itineraryEquipments;
    private Timestamp startTime;
    private Timestamp entTime;
    private Long timeDistance;
    private Long wayDistance;
    private Long activityTypeId;
    private Long eventId;
    private String description;
    private Long destinationId;

    public ArrayList<Long> getItineraryEquipments() {
        if(itineraryEquipments == null){
            itineraryEquipments = new ArrayList<>();
        }
        return itineraryEquipments;
    }

    public OutputAPIForm checkMandatoryForInsert(){
        OutputAPIForm retVal = Utility.checkNull(getOccasionId());
        retVal = retVal.isSuccess() ? Utility.checkNull(getSourceId()) : retVal;
        retVal = retVal.isSuccess() ? Utility.checkNull(getItineraryId()) : retVal;
        retVal = retVal.isSuccess() ? Utility.checkNull(getStartTime()) : retVal;
        retVal = retVal.isSuccess() ? Utility.checkNull(getEntTime()) : retVal;
        retVal = retVal.isSuccess() ? Utility.checkNull(getActivityTypeId()) : retVal;
        retVal = retVal.isSuccess() ? Utility.checkNull(getEventId()) : retVal;
        return retVal;
    }
}
