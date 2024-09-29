package com.env.service.dto;

import com.env.dao.entity.ItineraryDetail;
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
public class ItineraryDetailDto implements Comparable<ItineraryDetailDto>{
    private Long itineraryDetailId;
    private PlaceDto source;
    private Long itineraryId;
    private ArrayList<ItineraryDetailEquipmentDto> itineraryEquipments;
    private Timestamp startTime;
    private Timestamp entTime;
    private Long timeDistance;
    private Long wayDistance;
    private Long activityTypeId;
    private Long eventId;
    private String description;
    private PlaceDto destination;
    private Long occasionId;
    public ItineraryDetailDto(ItineraryDetail itineraryDetail){
        this(itineraryDetail.getItineraryDetailId(),
             new PlaceDto(itineraryDetail.getSourcePlace()),
             itineraryDetail.getItineraryId(),
             new ArrayList<ItineraryDetailEquipmentDto>(),
             itineraryDetail.getStartTime(),
             itineraryDetail.getEndTime(),
             itineraryDetail.getTimeDistance(),
             itineraryDetail.getWayDistance(),
             itineraryDetail.getActivityId(),
             itineraryDetail.getEventId(),
             itineraryDetail.getDescription(),
             itineraryDetail.getDestinationPlace() ==null ? null: new PlaceDto(itineraryDetail.getDestinationPlace()),
             null);
    }

    @Override
    public int compareTo(ItineraryDetailDto o) {
        int retVal = 0;
        try{
            if(this.getStartTime().before(o.getStartTime())){
                retVal = -1;
            }else{
                retVal = 1;
            }
        }catch (Exception e){
            retVal = 0;
        }
        return retVal;
    }
}
