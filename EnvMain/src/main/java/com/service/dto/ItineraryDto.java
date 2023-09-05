package com.service.dto;

import com.dao.entity.Itinerary;
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
public class ItineraryDto {
    private Long itineraryId;
    private Long occasionId;
    private Timestamp itineraryDate;
    private ArrayList<ItineraryDetailDto> itineraryDetailDtos;
    public ItineraryDto(Itinerary ent){
        this(ent.getItineraryId(), ent.getOccasionId(), ent.getItineraryDate(),null);
        ArrayList<ItineraryDetailDto> itineraryDetails = new ArrayList<>();
        for(ItineraryDetail itineraryDetail:ent.getItineraryDetails()){
            itineraryDetails.add(new ItineraryDetailDto(itineraryDetail));
        }
        this.setItineraryDetailDtos(itineraryDetails);
    }
}
