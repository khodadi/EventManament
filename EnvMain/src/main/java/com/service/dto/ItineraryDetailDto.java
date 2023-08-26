package com.service.dto;

import com.dao.entity.ItineraryDetail;
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
public class ItineraryDetailDto{
    private Long itineraryDetailId;
    private PlaceDto source;
    private Long itineraryId;
    private ArrayList<ItineraryDetailEquipmentDto> itineraryEquipments;
    private Timestamp startTime;
    private Timestamp entTime;
    private Long timeDistance;
    private Long wayDistance;
    private ActivityTypeDto activityType;
    private EventDto eventId;
    private String description;
    private PlaceDto destination;
    public ItineraryDetailDto(ItineraryDetail itineraryDetail){
        this(itineraryDetail.getItineraryDetailId(),
             new PlaceDto(itineraryDetail.getSourcePlaceId()),
             itineraryDetail.getItineraryId(),
             new ArrayList<ItineraryDetailEquipmentDto>(),
             itineraryDetail.getStartTime(),
             itineraryDetail.getEndTime(),
             itineraryDetail.getTimeDistance(),
             itineraryDetail.getWayDistance(),
             new ActivityTypeDto(itineraryDetail.getActivityId()),
             new EventDto(itineraryDetail.getEventId()),
             itineraryDetail.getDescription(),
             new PlaceDto(itineraryDetail.getDestinationPlaceId()));
    }
}
