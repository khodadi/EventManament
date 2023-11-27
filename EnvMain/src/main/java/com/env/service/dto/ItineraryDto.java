package com.env.service.dto;

import com.env.dao.entity.Itinerary;
import com.env.dao.entity.ItineraryDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDto implements Comparable<ItineraryDto> {
    private Long itineraryId;
    private Long occasionId;
    private Timestamp itineraryDate;
    private ArrayList<ItineraryDetailDto> itineraryDetailDtos;
    public ItineraryDto(Itinerary ent){
        this(ent.getItineraryId(), ent.getOccasionId(), ent.getItineraryDate(),null);
        if(Objects.nonNull(ent.getItineraryDetails())){
            ArrayList<ItineraryDetailDto> itineraryDetails = new ArrayList<>();
            for(ItineraryDetail itineraryDetail:ent.getItineraryDetails()){
                itineraryDetails.add(new ItineraryDetailDto(itineraryDetail));
            }
            this.setItineraryDetailDtos(itineraryDetails);
        }
    }

    @Override
    public int compareTo(ItineraryDto itineraryDto) {
        int retVal = 0;
        if(itineraryDto.getItineraryDate().after(this.getItineraryDate())){
            retVal = 1;
        }else{
            retVal = -1;
        }
        return retVal;
    }
}
