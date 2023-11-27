package com.service.dto;

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
    private Long sourceId;
    private Long itineraryId;
    private ArrayList<Long> itineraryEquipments;
    private Timestamp startTime;
    private Timestamp entTime;
    private Long timeDistance;
    private Long wayDistance;
    private Long activityType;
    private Long eventId;
    private String description;
    private Long destinationId;

    public ArrayList<Long> getItineraryEquipments() {
        if(itineraryEquipments == null){
            itineraryEquipments = new ArrayList<>();
        }
        return itineraryEquipments;
    }
}
